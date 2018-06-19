package com.example.nikita.vkapi.presentation.base

import com.example.nikita.vkapi.presentation.newsList.NewsListFragment

interface PresenterMVP {

        fun initPresenter()
        fun bindFragment(newsListFragment: NewsListFragment)
        fun unbindFragment()

}