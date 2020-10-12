package app.fdj.fdjtest.interactor

import app.fdj.fdjtest.MainContract
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class MainInteractor : MainContract.Interactor {

    companion object {
        private const val fdjBaseUrl: String = "https://www.thesportsdb.com/api/v1/json/1"
        private const val leaguesUrl = "$fdjBaseUrl/search_all_leagues.php?s=Soccer"
        private const val teamsByLeagueUrl = "$fdjBaseUrl/search_all_teams.php"
    }

    override fun loadLeagueList(interactorOutput: (result: Result<Json, FuelError>) -> Unit) {
        leaguesUrl.httpGet().responseJson { _, _, result ->
            interactorOutput(result)
        }
    }

    override fun loadTeamListByLeague(interactorOutput: (result: Result<Json, FuelError>) -> Unit, keyword: String?) {
        "$teamsByLeagueUrl?l=$keyword".httpGet().responseJson { _, _, result ->
            interactorOutput(result)
        }
    }
}