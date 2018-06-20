package com.example.nikita.vkapi.presentation.newsList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nikita.vkapi.R
import com.example.nikita.vkapi.data.models.NewsModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card.view.*
import kotlinx.android.synthetic.main.erroe_card.view.*

class CardAdapter(val newsModelList: MutableList<NewsModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var listener: OnItemClick

    val newsType = 0
    val errorType = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            errorType -> ErrorHolder(LayoutInflater.from(parent.context).inflate(R.layout.erroe_card,
                    parent, false))
            else -> NewsHolder(LayoutInflater.from(parent.context).inflate(R.layout.card,
                    parent, false))

        }
    }

    override fun getItemCount(): Int {
        return if (newsModelList.isNotEmpty()) newsModelList.size else 1
    }

    override fun getItemViewType(position: Int) =
            if (newsModelList.isEmpty()) errorType else newsType

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is NewsHolder -> {
                val card = newsModelList[position]
                holder.dateWriten.text = card.date
                holder.content.text = card.content
                if (card.imagePath.isNotEmpty())
                    Picasso.get().load(card.imagePath).into(holder.image)
            }

        }

    }

    fun setOnItemClickListener(listener: OnItemClick) {
        this.listener = listener
    }

    fun updateNews(newsList: List<NewsModel>) {
        newsModelList.clear()
        newsModelList.addAll(newsList)
        notifyDataSetChanged()
    }

    inner class NewsHolder(v: View) : RecyclerView.ViewHolder(v) {
        val dateWriten = v.data
        val content = v.content
        val image = v.image
        val card = v.card

        init {
            card.setOnClickListener({ listener.onItemClick(newsModelList[adapterPosition])})
        }
    }

    inner class ErrorHolder(v: View) : RecyclerView.ViewHolder(v) {
        val textError = v.textError
    }

    interface OnItemClick {
        fun onItemClick(newsModel: NewsModel)
    }
}