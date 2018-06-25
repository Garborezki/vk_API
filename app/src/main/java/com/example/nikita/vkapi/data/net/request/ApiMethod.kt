package com.example.nikita.vkapi.data.net.request

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMethod {
    @GET("method/wall.get?")
    fun newsList(@Query("owner_id") group: Int,
                 @Query("offset") offSet: Int,
                 @Query("count") count: Int,
                 @Query("access_token") token: String,
                 @Query("v") version: Double): Single<String>
}