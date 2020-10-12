package app.fdj.fdjtest.view.activities.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.fdj.fdjtest.BaseApplication
import app.fdj.fdjtest.DetailContract
import app.fdj.fdjtest.R
import app.fdj.fdjtest.entity.Player
import app.fdj.fdjtest.presenter.DetailPresenter
import app.fdj.fdjtest.view.adapters.PlayerListAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.toast
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Back
import ru.terrakok.cicerone.commands.Command

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private var presenter: DetailContract.Presenter? = null
    private val recyclerView: RecyclerView? by lazy { rv_detail }
    private val progress: ProgressBar? by lazy { progress_bar_detail }

    companion object {
        const val TAG = "DetailActivity"
    }

    private val navigator: Navigator? by lazy {
        object : Navigator {
            override fun applyCommand(command: Command?) {
                if (command is Back) {
                    back(command)
                }
            }

            private fun back(command: Back) {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        presenter = DetailPresenter(this)
        recyclerView?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView?.adapter = PlayerListAdapter(null)
    }

    override fun onResume() {
        super.onResume()

        supportActionBar?.let {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        BaseApplication.INSTANCE.cicerone.navigatorHolder.setNavigator(navigator)

        val argument = intent.getStringExtra("teamId")
        argument?.let {
            presenter?.onViewCreated(argument)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                presenter?.onBackClicked()
                true
            }
            else -> false
        }
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

    override fun populatePlayersData(data: List<Player>) {
        (recyclerView?.adapter as PlayerListAdapter).updateData(data)
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
