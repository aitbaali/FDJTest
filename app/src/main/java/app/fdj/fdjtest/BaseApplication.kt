package app.fdj.fdjtest

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class BaseApplication : Application() {

    companion object {
        lateinit var INSTANCE: BaseApplication
    }

    init {
        INSTANCE = this
    }

    // router
    lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
        this.initCicerone()
    }

    private fun initCicerone() {
        this.cicerone = Cicerone.create()
    }
}