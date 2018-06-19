package com.example.nikita.vkapi.other

import android.os.Bundle
import com.example.nikita.vkapi.presentation.cardInfo.CardInfoFragment
import com.example.nikita.vkapi.presentation.newsList.NewsListFragment

class FragmentCreator {

    enum class FragmentFabric {
        NEWS_LIST, CARD_INFO
    }

    fun createFragment(fragmentFabric: FragmentFabric, bundle: Bundle) = when (fragmentFabric) {
        FragmentFabric.NEWS_LIST -> NewsListFragment.newInstance(bundle)
        FragmentFabric.CARD_INFO -> CardInfoFragment.newInstance(bundle)
    }

}