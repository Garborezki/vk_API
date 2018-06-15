package com.example.nikita.vk_api.other

import android.content.Intent
import com.example.nikita.vk_api.MainActivity
import com.vk.sdk.VKSdk
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker



class Application : android.app.Application() {

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                val intent = Intent(this@Application, MainActivity::class.java)

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)

            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)
    }
}