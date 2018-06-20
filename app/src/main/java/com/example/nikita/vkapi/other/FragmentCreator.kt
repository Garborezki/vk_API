package com.example.nikita.vkapi.other

import android.os.Bundle
import com.example.nikita.vkapi.presentation.cardInfo.CardInfoFragment
import com.example.nikita.vkapi.presentation.newsList.NewsListFragment

class FragmentCreator {



    fun createFragment(fragmentFabric: FragmentType.FragmentFabric, bundle: Bundle) = when (fragmentFabric) {
        FragmentType.FragmentFabric.NEWS_LIST -> NewsListFragment.newInstance(bundle)
        FragmentType.FragmentFabric.CARD_INFO -> CardInfoFragment.newInstance(bundle)
    }

}
object FragmentType {
    enum class FragmentFabric {
        NEWS_LIST, CARD_INFO
    }

    const val NEWS_LIST = "newsList"
    const val CARD_INFO = "cardInfo"
}
