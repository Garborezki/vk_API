package com.example.nikita.vkapi.presentation.newsList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikita.vkapi.R
import com.example.nikita.vkapi.data.models.NewsModel
import com.example.nikita.vkapi.other.FragmentCreator
import com.example.nikita.vkapi.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_news_list.view.*


class NewsListFragment : Fragment() {

    val presenter = NewsListPresenter()
    lateinit var mainView: View

    companion object {
        fun newInstance(bundle: Bundle): Fragment {
            val fragment = NewsListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.bindFragment(this)
        presenter.initPresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        mainView = view
        initRecyclerView()
        return view
    }



    fun initRecyclerView() {
        mainView.recyclerView.layoutManager = LinearLayoutManager(activity!!)
        mainView.recyclerView.adapter = presenter.adapter
    }

    fun startFragmentCardInfo(newsModel: NewsModel) {
        val activity = activity as MainActivity
        val bundle = Bundle()
        bundle.putSerializable(NewsModel::class.java.name, newsModel)
        activity.navigateTo(FragmentCreator.FragmentFabric.CARD_INFO, bundle, true)
    }

    override fun onDetach() {
        super.onDetach()
        presenter.unbindFragment()
    }


}