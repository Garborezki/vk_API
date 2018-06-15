package com.example.nikita.vk_api.presentation.newsList

import com.example.nikita.vk_api.data.models.NewsModel

interface NewsList {

    interface View {
        fun initFragment(v: android.view.View)
        fun startFragmentCardInfo(newsModel: NewsModel)
    }

    interface Presenter {
        fun initPresenter()
        fun bindFragment(newsListFragment: NewsListFragment)
        fun unbindFragment()
    }
}