package com.example.nikita.vkapi.interactors

import com.example.nikita.vkapi.data.dataBase.DBReposetory
import com.example.nikita.vkapi.data.models.NewsModel
import com.example.nikita.vkapi.data.net.requestBuilder.RequestBuilder
import com.example.nikita.vkapi.other.NewsEvent
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Date
import java.sql.Timestamp

class PostGroup {

    private var disposable: Disposable? = null

    fun groupResponse() {

        val version = 5.80
        val token = "e5b1fd320f28caa71e63f232ae6d6796089aaf713a04007db229c922b3fd06f8e7c8ace63de63e4b8b1be"

        RequestBuilder.getRetrofit().newsList(-77522551, 0, 15, token, version)
                .map { JSONObject(it) }
                .subscribe(object : SingleObserver<JSONObject> {
                    override fun onSuccess(responseVk: JSONObject) {
                        val newsList = ArrayList<NewsModel>()
                        val jsonObjects = responseVk.getJSONObject("response")
                        val item = jsonObjects.get("items") as JSONArray
                        for (i in 0..(item.length() - 1)) {
                            val currentNews = NewsModel()
                            val currentItem = item.get(i) as JSONObject
                            currentNews.id = currentItem.getInt("id")
                            val timeStamp = Timestamp(currentItem.getString("date").toLong())
                            currentNews.date = Date(timeStamp.time).toString()
                            currentNews.content = currentItem.getString("text")
                            val photo = currentItem.getJSONArray("attachments")
                            try {
                                val firstPhoto = photo.get(0) as JSONObject
                                val firstPhotoNews = firstPhoto.get("photo") as JSONObject
                                currentNews.imagePath = firstPhotoNews.getString("photo_604")
                            } catch (e: Exception) {

                            }
                            newsList.add(currentNews)
                        }
                        DBReposetory.insertNews(newsList)
                        EventBus.getDefault().post(NewsEvent())
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })


    }

    fun unsubscribe() {
        disposable?.dispose()
    }
}