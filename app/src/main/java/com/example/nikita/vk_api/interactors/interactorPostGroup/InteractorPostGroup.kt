package com.example.nikita.vk_api.interactors.interactorPostGroup

import com.example.nikita.vk_api.data.models.NewsModel
import com.example.nikita.vk_api.presentation.newsList.NewsList
import com.vk.sdk.api.*
import com.vk.sdk.api.methods.VKApiWall
import org.json.JSONArray
import org.json.JSONObject

class InteractorPostGroup {


    //var newsList = ArrayList<NewsModel>()


    fun requestVk(newsList: ArrayList<NewsModel>): ArrayList<NewsModel> {

        val request = VKApiWall().get(VKParameters.from(VKApiConst.OWNER_ID, "-77522551", VKApiConst.COUNT, 2))
        request.executeWithListener(object : VKRequest.VKRequestListener() {

            override fun onComplete(response: VKResponse?) {
                try {
                    val jsonObjects = response!!.json.get("response") as JSONObject
                    val jsonArray = jsonObjects.get("items") as JSONArray
                    val currentNews = NewsModel()
                    for (i in 0..jsonArray.length()) {
                        val currentItem = jsonArray.get(i) as JSONObject
                        currentNews.content = currentItem.getString("text")
                        newsList.add(currentNews)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onError(error: VKError?) {
                //Do error stuff
            }

            override fun attemptFailed(request: VKRequest?, attemptNumber: Int, totalAttempts: Int) {
                //I don't really believe in progress
            }
        })
        return newsList
    }

}