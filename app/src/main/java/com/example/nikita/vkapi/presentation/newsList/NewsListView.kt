package com.example.nikita.vkapi.presentation.newsList

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.nikita.vkapi.data.models.NewsModel

interface NewsListView: MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun startFragmentCardInfo(newsModel: NewsModel)

    @StateStrategyType(SkipStrategy::class)
    fun makeErrorToast(errorMessage: String)

}