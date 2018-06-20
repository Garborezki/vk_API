package com.example.nikita.vkapi

import com.example.nikita.vkapi.data.dataBase.DBReposetory
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router




class Application : android.app.Application() {

    object SampleApplication {
        lateinit var INSTANCE: Application
    }
    private lateinit var cicerone: Cicerone<Router>

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        SampleApplication.INSTANCE = this
        cicerone = Cicerone.create()
        DBReposetory.init(applicationContext)
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
    }

    fun getNavigatorHolder(): NavigatorHolder {
        return cicerone.navigatorHolder
    }

    fun getRouter(): Router {
        return cicerone.router
    }
}