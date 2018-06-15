package com.example.nikita.vk_api.presentation.newsList

import com.example.nikita.vk_api.data.models.NewsModel
import com.example.nikita.vk_api.interactors.interactorPostGroup.InteractorPostGroup
import java.util.*

class NewsListPresenter: NewsList.Presenter {


    var newsListFragment: NewsListFragment? = null
    val interactorPostGroup = InteractorPostGroup()
    lateinit var adapter: CardAdapter

    override fun initPresenter() {
        var newsList = ArrayList<NewsModel>()
        adapter = CardAdapter(interactorPostGroup.requestVk(newsList))
        adapter.setOnItemClickListener(ItemClickListener())
    }

    override fun bindFragment(newsListFragment: NewsListFragment) {
        this.newsListFragment = newsListFragment
    }

    override fun unbindFragment() {
        newsListFragment = null
    }

    private fun createExampleList() : ArrayList<NewsModel> {
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

    inner class ItemClickListener : CardAdapter.onItemClick {
        override fun onItemClick(newsModel: NewsModel) {
           newsListFragment!!.startFragmentCardInfo(newsModel)
        }
    }


}