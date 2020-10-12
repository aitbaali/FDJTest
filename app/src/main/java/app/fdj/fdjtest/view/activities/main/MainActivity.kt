package app.fdj.fdjtest.view.activities.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.fdj.fdjtest.BaseApplication
import app.fdj.fdjtest.MainContract
import app.fdj.fdjtest.R
import app.fdj.fdjtest.entity.League
import app.fdj.fdjtest.entity.Team
import app.fdj.fdjtest.presenter.MainPresenter
import app.fdj.fdjtest.view.activities.detail.DetailActivity
import app.fdj.fdjtest.view.adapters.TeamListAdapter
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward

class MainActivity : AppCompatActivity(), MainContract.View {

    private var presenter: MainContract.Presenter? = null
    private val recyclerView: RecyclerView? by lazy { rv_main }
    private val progress: ProgressBar? by lazy { progress_bar_main }
    private val searchView: FloatingSearchView? by lazy { floating_search_view }

    companion object {
        const val TAG = "MainActivity"
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command?) {
                if (command is Forward) {
                    forward(command)
                }
            }

            private fun forward(command: Forward) {
                val data = (command.transitionData as String)
                when (command.screenKey) {
                    DetailActivity.TAG -> {
                        startActivity(Intent(this@MainActivity, DetailActivity::class.java).putExtra("teamId", data))
                    }
                    else -> {
                        Log.e(TAG, "Cicerone error : " + command.screenKey)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

        searchView?.setOnQueryChangeListener { oldQuery, newQuery ->
            presenter?.onSearchViewQueryChangeListener(oldQuery, newQuery)
        }

        searchView?.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String?) {
                presenter?.onSearchViewAction(currentQuery)
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion?) {
                val league = searchSuggestion as League
                presenter?.onSearchViewSuggestionClicked(league)
                searchView?.setSearchText(league.name)
                searchView?.clearSearchFocus()
            }
        })

        recyclerView?.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = TeamListAdapter({ team -> presenter?.onItemClicked(team?.id.toString()) }, null)
    }

    override fun onResume() {
        super.onResume()

        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)
        presenter?.onViewCreated()
    }

    override fun onPause() {
        super.onPause()

        BaseApplication.INSTANCE.cicerone.navigatorHolder.removeNavigator()
    }

    override fun showLoading() {
        recyclerView?.isEnabled = false
        progress?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        recyclerView?.isEnabled = true
        progress?.visibility = View.GONE
    }

    override fun populateLeagueData(data: List<League>) {
        searchView?.swapSuggestions(data)
    }

    override fun populateTeamData(data: List<Team>) {
        (recyclerView?.adapter as TeamListAdapter).updateData(data)
    }

    override fun showInfoMessage(message: String) {
        toast(message)
    }

    override fun onDestroy() {
        presenter?.onViewDestroyed()
        presenter = null

        super.onDestroy()
    }
}
