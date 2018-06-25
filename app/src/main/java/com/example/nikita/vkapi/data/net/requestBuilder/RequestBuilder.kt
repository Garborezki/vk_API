package com.example.nikita.vkapi.data.net.requestBuilder

import com.example.nikita.vkapi.data.net.request.ApiMethod
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RequestBuilder {

    const val url = "https://api.vk.com/"

    fun getRetrofit(): ApiMethod {
        return Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(ApiMethod::class.java)
    }
}