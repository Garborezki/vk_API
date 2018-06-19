package com.example.nikita.vkapi.data.models

import java.io.Serializable


class NewsModel : Serializable {
    var id = 0
    var date = ""
    var content = ""
    var imagePath: String = ""

}