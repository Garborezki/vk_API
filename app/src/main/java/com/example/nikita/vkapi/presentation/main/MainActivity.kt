package com.example.nikita.vkapi.presentation.main


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.nikita.vkapi.Application
import com.example.nikita.vkapi.R
import com.example.nikita.vkapi.other.FragmentType
import com.example.nikita.vkapi.presentation.cardInfo.CardInfoFragment
import com.example.nikita.vkapi.presentation.newsList.NewsListFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportFragmentNavigator


class MainActivity : AppCompatActivity() {

    private val navigator: Navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.fragment_container) {

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            return when (screenKey) {
                FragmentType.CARD_INFO -> {
                    CardInfoFragment.newInstance(data as Bundle)
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
        Application.SampleApplication.INSTANCE.getRouter().navigateTo(FragmentType.NEWS_LIST, Bundle())

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
