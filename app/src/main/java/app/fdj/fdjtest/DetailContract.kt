package app.fdj.fdjtest

import app.fdj.fdjtest.entity.Player
import com.github.kittinunf.fuel.android.core.Json
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.result.Result

interface DetailContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun populatePlayersData(data: List<Player>)
        fun showInfoMessage(message: String)
    }

    interface Presenter {
        fun onViewCreated(teamId: String?)
        fun onViewDestroyed()
        fun onBackClicked()
    }

    interface Interactor {
        fun loadPlayersList(interactorOutput: (result: Result<Json, FuelError>) -> Unit, teamId: String?)
    }

    interface InteractorOutput {
        fun onLoadPlayerQuerySuccess(data: List<Player>)
        fun onQueryError()
    }
}