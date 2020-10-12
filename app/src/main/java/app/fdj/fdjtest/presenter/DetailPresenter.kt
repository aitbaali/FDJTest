package app.fdj.fdjtest.presenter

import app.fdj.fdjtest.BaseApplication
import app.fdj.fdjtest.DetailContract
import app.fdj.fdjtest.entity.Player
import app.fdj.fdjtest.interactor.DetailInteractor
import com.github.kittinunf.result.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import ru.terrakok.cicerone.Router

class DetailPresenter(private var view: DetailContract.View?) : DetailContract.Presenter,
    DetailContract.InteractorOutput {

    private val router: Router? by lazy { BaseApplication.INSTANCE.cicerone.router }
    private var interactor: DetailContract.Interactor? = DetailInteractor()

    override fun onViewCreated(teamId: String?) {
        view?.showLoading()
        interactor?.loadPlayersList({ result ->
            when (result) {
                is Result.Failure -> {
                    this.onQueryError()
                }
                is Result.Success -> {
                    val playersJsonObject = result.get().obj()
                    val type = object : TypeToken<List<Player>>() {}.type
                    try {
                        val data: List<Player> =
                            Gson().fromJson<List<Player>>(playersJsonObject.getJSONArray("player").toString(), type)
                        this.onLoadPlayerQuerySuccess(data)
                    } catch (e: JSONException) {
                        this.onQueryError()
                    }
                }
            }
        }, teamId)
    }

    override fun onLoadPlayerQuerySuccess(data: List<Player>) {
        view?.hideLoading()
        view?.populatePlayersData(data)
    }

    override fun onQueryError() {
        view?.hideLoading()
        view?.showInfoMessage("Error on loading data")
    }

    override fun onBackClicked() {
        router?.exit()
    }

    override fun onViewDestroyed() {
        view = null
        interactor = null
    }
}
