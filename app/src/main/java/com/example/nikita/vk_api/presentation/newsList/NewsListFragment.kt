package com.example.nikita.vk_api.presentation.newsList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikita.vk_api.R
import com.example.nikita.vk_api.MainActivity
import com.example.nikita.vk_api.data.models.NewsModel
import com.example.nikita.vk_api.other.FragmentCreator
import kotlinx.android.synthetic.main.fragment_news_list.view.*


class NewsListFragment : Fragment(), NewsList.View {

    val presenter = NewsListPresenter()

    companion object {
        fun newInstance(bundle: Bundle) : Fragment {
            val fragment = NewsListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        initFragment(view)
        return view
    }

    override fun initFragment(v: View) {

        presenter.bindFragment(this)
        presenter.initPresenter()
        v.recyclerView.layoutManager = LinearLayoutManager(activity!!)
        v.recyclerView.adapter = presenter.adapter

    }

    override fun startFragmentCardInfo(newsModel: NewsModel) {
        val activity = activity as MainActivity
        val bundle = Bundle()
        bundle.putSerializable(NewsModel::class.java.name, newsModel )
        activity.navigateTo(FragmentCreator.FragmentFabric.CARD_INFO, bundle, true)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.unbindFragment()
    }


}