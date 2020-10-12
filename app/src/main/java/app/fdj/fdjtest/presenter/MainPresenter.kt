package app.fdj.fdjtest.presenter

import android.util.Log
import app.fdj.fdjtest.BaseApplication
import app.fdj.fdjtest.MainContract
import app.fdj.fdjtest.entity.League
import app.fdj.fdjtest.entity.Team
import app.fdj.fdjtest.interactor.MainInteractor
import app.fdj.fdjtest.view.activities.detail.DetailActivity
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import ru.terrakok.cicerone.Router

class MainPresenter(private var view: MainContract.View?) : MainContract.Presenter, MainContract.InteractorOutput {

    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }
    private var interactor: MainContract.Interactor? = MainInteractor()
    private lateinit var leaguesList: List<League>

    override fun onViewCreated() {
        view?.showLoading()
        interactor?.loadLeagueList { result ->
            when (result) {
                is Result.Failure -> {
                    this.onQueryError()
                }
                is Result.Success -> {
                    val leaguesJsonObject = result.get().obj()
                    val type = object : TypeToken<List<League>>() {}.type
                    val data: List<League> =
                        Gson().fromJson<List<League>>(leaguesJsonObject.getJSONArray("countrys").toString(), type)
                    this.onLoadLeagueQuerySuccess(data)
                }
            }
        }
    }

    override fun onViewDestroyed() {
        view = null
        interactor = null
    }

    override fun onItemClicked(teamId: String?) {
        router?.navigateTo(DetailActivity.TAG, teamId)
    }

    override fun onSearchViewQueryChangeListener(oldQuery: String?, newQuery: String?) {
        val filter = leaguesList.filter { it.name.contains(newQuery.toString(), true) }
        view?.populateLeagueData(filter)
    }

    override fun onSearchViewAction(currentQuery: String?) {
        Log.d("CURRENT QUERY : ", currentQuery)
    }

    override fun onSearchViewSuggestionClicked(searchSuggestion: SearchSuggestion?) {
        view?.showLoading()
        interactor?.loadTeamListByLeague({ result ->
            when (result) {
                is Result.Failure -> {
                    this.onQueryError()
                }
                is Result.Success -> {
                    val teamsJsonObject = result.get().obj()
                    val type = object : TypeToken<List<Team>>() {}.type
                    try {
                        val data: List<Team> =
                            Gson().fromJson<List<Team>>(teamsJsonObject.getJSONArray("teams").toString(), type)
                        this.onLoadTeamQuerySuccess(data)
                    } catch (e: JSONException) {
                        this.onQueryError()
                    }
                }
            }
        }, (searchSuggestion as League).name)
    }

    override fun onLoadLeagueQuerySuccess(data: List<League>) {
        view?.hideLoading()
        this.leaguesList = data
    }

    override fun onLoadTeamQuerySuccess(data: List<Team>) {
        view?.hideLoading()
        view?.populateTeamData(data)
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showInfoMessage("Error when loading data")
    }
}