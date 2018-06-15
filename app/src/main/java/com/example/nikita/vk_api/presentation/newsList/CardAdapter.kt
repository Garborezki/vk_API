package com.example.nikita.vk_api.presentation.newsList

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikita.vk_api.R
import com.example.nikita.vk_api.data.models.NewsModel
import kotlinx.android.synthetic.main.card.view.*
import java.util.*

class CardAdapter(val newsModelList: List<NewsModel>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    lateinit var listener: onItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false))
    }

    override fun getItemCount(): Int {
        return newsModelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = newsModelList.get(position)
        holder.dateWriten.text = card.date
        holder.content.text = card.content

    }

    fun setOnItemClickListener(listener: onItemClick) {
        this.listener = listener
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val dateWriten = v.data
        val content = v.content
        val image = v.image
        val card = v.card

        init {
            card.setOnClickListener({listener.onItemClick(newsModelList[adapterPosition])})
        }

    }

    interface onItemClick {
        fun onItemClick(newsModel: NewsModel)
    }
}