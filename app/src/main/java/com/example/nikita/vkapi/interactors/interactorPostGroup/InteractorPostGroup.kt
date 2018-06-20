package com.example.nikita.vkapi.interactors.interactorPostGroup

import com.example.nikita.vkapi.data.dataBase.DBReposetory
import com.example.nikita.vkapi.data.models.NewsModel
import com.example.nikita.vkapi.other.NewsEvent
import com.vk.sdk.api.*
import com.vk.sdk.api.methods.VKApiWall
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.sql.Timestamp
import java.util.Date
import kotlin.collections.ArrayList

class InteractorPostGroup {


    var newsList = ArrayList<NewsModel>()


    fun requestVk() {

        val request = VKApiWall().get(VKParameters.from(VKApiConst.OWNER_ID, "-77522551", VKApiConst.COUNT, 15))
        request.executeWithListener(object : VKRequest.VKRequestListener() {

            override fun onComplete(response: VKResponse?) {

                try {
                    val jsonObjects = response!!.json.get("response") as JSONObject
                    val item = jsonObjects.get("items") as JSONArray
                    for (i in 0..(item.length() - 1) ) {
                        val currentNews = NewsModel()
                        val currentItem = item.get(i) as JSONObject
                        currentNews.id = currentItem.getInt("id")
                        val timeStamp = Timestamp(currentItem.getString("date").toLong())
                        currentNews.date =  Date(timeStamp.time).toString()
                        currentNews.content = currentItem.getString("text")
                        val photo = currentItem.getJSONArray("attachments")
                        try {
                            val firstPhoto = photo.get(0) as JSONObject
                            val photo_604 = firstPhoto.get("photo") as JSONObject
                            currentNews.imagePath = photo_604.getString("photo_604")
                        } catch (e: Exception) {

                        }
                        newsList.add(currentNews)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                DBReposetory.insertNews(newsList)
                EventBus.getDefault().post(NewsEvent())
            }

            override fun onError(error: VKError?) {

                EventBus.getDefault().post(NewsEvent(error!!.errorMessage))
            }

            override fun attemptFailed(request: VKRequest?, attemptNumber: Int, totalAttempts: Int) {
                //I don't really believe in progress
            }
        })

    }

}