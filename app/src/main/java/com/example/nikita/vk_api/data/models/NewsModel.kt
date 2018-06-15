package com.example.nikita.vk_api.data.models

import java.io.Serializable


class NewsModel: Serializable  {

    lateinit var date: String
    var content = ""
    var imagePath: String? = null

}