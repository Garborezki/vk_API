package com.example.nikita.vkapi.presentation.newsList

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.nikita.vkapi.R
import com.example.nikita.vkapi.data.models.NewsModel
import com.example.nikita.vkapi.other.FragmentCreator
import com.example.nikita.vkapi.presentation.main.MainActivity
import kotlinx.android.synthetic.main.fragment_news_list.view.*


class NewsListFragment : MvpAppCompatFragment(), NewsListView {

    @InjectPresenter
    lateinit var presenter: NewsListPresenter

    private lateinit var mainView: View

    companion object {
        fun newInstance(bundle: Bundle): Fragment {
            val fragment = NewsListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)
        mainView = view
        initRecyclerView()
        return view
    }

    private fun initRecyclerView() {
        mainView.recyclerView.layoutManager = LinearLayoutManager(context)
        mainView.recyclerView.adapter = presenter.adapter
    }

    override fun startFragmentCardInfo(newsModel: NewsModel) {
        val activity = activity as MainActivity
        val bundle = Bundle()
        bundle.putSerializable(NewsModel::class.java.name, newsModel)
        activity.navigateTo(FragmentCreator.FragmentFabric.CARD_INFO, bundle, true)
    }

    override fun makeErrorToast(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

}