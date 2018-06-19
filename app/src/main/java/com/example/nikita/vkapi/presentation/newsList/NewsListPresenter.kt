package com.example.nikita.vkapi.presentation.newsList

import com.example.nikita.vkapi.data.dataBase.DBReposetory
import com.example.nikita.vkapi.data.models.NewsModel
import com.example.nikita.vkapi.interactors.interactorPostGroup.InteractorPostGroup
import com.example.nikita.vkapi.other.NewsEvent
import com.example.nikita.vkapi.presentation.base.PresenterMVP
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

class NewsListPresenter: PresenterMVP {


    var newsListFragment: NewsListFragment? = null
    private val interactorPostGroup = InteractorPostGroup()
    lateinit var adapter: CardAdapter

    override fun initPresenter() {

        interactorPostGroup.requestVk()
        adapter = CardAdapter(DBReposetory.getNewsList())
        adapter.setOnItemClickListener(ItemClickListener())
    }

    override fun bindFragment(newsListFragment: NewsListFragment) {
        this.newsListFragment = newsListFragment
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun setNewsList(newsEvent: NewsEvent) {

        adapter.updateNews(DBReposetory.getNewsList())
    }

    override fun unbindFragment() {
        newsListFragment = null
        EventBus.getDefault().unregister(this)
    }

    private fun createExampleList(): ArrayList<NewsModel> {
        val item = NewsModel()
        item.date = "Дата опубликования"
        item.content = "Текст какой то новости, которая отображается в карточке и занимает как минимум две строки"
        val itemList = ArrayList<NewsModel>()
        itemList.add(item)
        itemList.add(item)
        itemList.add(item)
        itemList.add(item)
        itemList.add(item)
        return itemList
    }

    inner class ItemClickListener : CardAdapter.OnItemClick {
        override fun onItemClick(newsModel: NewsModel) {
            newsListFragment!!.startFragmentCardInfo(newsModel)
        }
    }


}