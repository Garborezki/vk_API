package com.example.nikita.vk_api.presentation.cardInfo

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikita.vk_api.R
import com.example.nikita.vk_api.data.models.NewsModel
import kotlinx.android.synthetic.main.card_info.*

class CardInfoFragment : Fragment() {

    lateinit var news: NewsModel

    companion object {
        fun newInstance(bundle: Bundle): Fragment {
            val fragment = CardInfoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        news = arguments!!.getSerializable(NewsModel::class.java.name) as NewsModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.card_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data.text = news.date
        content.text = news.content
    }


}