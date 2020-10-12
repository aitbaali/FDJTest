package app.fdj.fdjtest.interactor

import app.fdj.fdjtest.DetailContract
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.android.extension.responseJson
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result

class DetailInteractor : DetailContract.Interactor {

    companion object {
        const val playersUrl: String = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_players.php"
    }

    override fun loadPlayersList(interactorOutput: (result: Result<Json, FuelError>) -> Unit, teamId: String?) {
        "$playersUrl?id=$teamId".httpGet().responseJson { _, _, result ->
            interactorOutput(result)
        }
    }
}
