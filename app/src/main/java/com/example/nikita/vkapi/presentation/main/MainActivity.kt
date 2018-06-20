package com.example.nikita.vkapi.presentation.main


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.nikita.vkapi.Application
import com.example.nikita.vkapi.R
import com.example.nikita.vkapi.other.FragmentCreator
import com.example.nikita.vkapi.other.FragmentType
import com.example.nikita.vkapi.presentation.cardInfo.CardInfoFragment
import com.example.nikita.vkapi.presentation.newsList.NewsListFragment
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportFragmentNavigator


class MainActivity : AppCompatActivity() {


    val fragmentCreator = FragmentCreator()

    private val navigator: Navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.fragment_container) {

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            return when (screenKey) {
                FragmentType.CARD_INFO -> {
                    CardInfoFragment.newInstance(Bundle())
                }
                FragmentType.NEWS_LIST -> {
                    NewsListFragment.newInstance(data as Bundle)
                }
                else -> {
                    NewsListFragment.newInstance(data as Bundle)
                }
            }
        }

        override fun exit() {
            finish()
        }

        override fun showSystemMessage(message: String?) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        VKSdk.login(this, VKScope.WALL)
        navigateTo(FragmentType.FragmentFabric.NEWS_LIST)

    }

    fun navigateTo(fragmentFabric: FragmentType.FragmentFabric, bundle: Bundle = Bundle(),
                   isPutBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,
                fragmentCreator.createFragment(fragmentFabric, bundle))
        if (isPutBackStack) {
            transaction.addToBackStack(fragmentFabric.name)
        }

        transaction.commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object : VKCallback<VKAccessToken> {

                    override fun onResult(res: VKAccessToken) {
                    }

                    override fun onError(error: VKError) {

                    }

                })) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onResume() {
        super.onResume()
        Application.SampleApplication.INSTANCE.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        Application.SampleApplication.INSTANCE.getNavigatorHolder().removeNavigator()
    }

}
