package app.fdj.fdjtest

import app.fdj.fdjtest.entity.League
import app.fdj.fdjtest.entity.Team
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result

interface MainContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun populateLeagueData(data: List<League>)
        fun populateTeamData(data: List<Team>)
        fun showInfoMessage(message: String)
    }

    interface Presenter {
        fun onViewCreated()
        fun onViewDestroyed()
        fun onItemClicked(teamId: String?)
        fun onSearchViewQueryChangeListener(oldQuery: String?, newQuery: String?)
        fun onSearchViewAction(currentQuery: String?)
        fun onSearchViewSuggestionClicked(searchSuggestion: SearchSuggestion?)
    }

    interface Interactor {
        fun loadLeagueList(interactorOutput: (result: Result<Json, FuelError>) -> Unit)
        fun loadTeamListByLeague(interactorOutput: (result: Result<Json, FuelError>) -> Unit, keyword: String?)
    }

    interface InteractorOutput {
        fun onLoadLeagueQuerySuccess(data: List<League>)
        fun onLoadTeamQuerySuccess(data: List<Team>)
        fun onQueryError()
    }
}