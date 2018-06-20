package com.example.nikita.vkapi.presentation.newsList

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.nikita.vkapi.Application
import com.example.nikita.vkapi.data.dataBase.DBReposetory
import com.example.nikita.vkapi.data.models.NewsModel
import com.example.nikita.vkapi.interactors.interactorPostGroup.InteractorPostGroup
import com.example.nikita.vkapi.other.FragmentType
import com.example.nikita.vkapi.other.NewsEvent
import com.example.nikita.vkapi.presentation.base.PresenterMVP
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@InjectViewState
class NewsListPresenter : MvpPresenter<NewsListView>(), PresenterMVP {

    //var newsListFragment: NewsListFragment? = null
    private val interactorPostGroup = InteractorPostGroup()
    lateinit var adapter: CardAdapter

    init {
        initPresenter()
    }

    override fun initPresenter() {

        EventBus.getDefault().register(this)
        interactorPostGroup.requestVk()
        adapter = CardAdapter(DBReposetory.getNewsList())
        adapter.setOnItemClickListener(object : CardAdapter.OnItemClick {
            override fun onItemClick(newsModel: NewsModel) {
               // viewState.startFragmentCardInfo(newsModel)
                val bundle = Bundle()
                bundle.putSerializable(NewsModel::class.java.name, newsModel)
                Application.SampleApplication.INSTANCE.getRouter().navigateTo(FragmentType.CARD_INFO, bundle)
            }
        })
    }

    override fun bindFragment(newsListFragment: NewsListFragment) {
        // this.newsListFragment = newsListFragment

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun setNewsList(newsEvent: NewsEvent) {
        if (newsEvent.errorMessage.isNotEmpty())
            viewState.makeErrorToast(newsEvent.errorMessage)
        adapter.updateNews(DBReposetory.getNewsList())

    }

    override fun unbindFragment() {
        //newsListFragment = null
        EventBus.getDefault().unregister(this)
    }

}