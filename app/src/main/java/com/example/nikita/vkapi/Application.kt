package com.example.nikita.vkapi

import com.example.nikita.vkapi.data.dataBase.DBReposetory
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router




class Application : android.app.Application() {

    object SampleApplication {
        lateinit var INSTANCE: Application
    }
    private lateinit var cicerone: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        SampleApplication.INSTANCE = this
        cicerone = Cicerone.create()
        DBReposetory.init(applicationContext)

    }

    fun getNavigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }

    fun getRouter(): Router {
        return cicerone.router
    }
}