package com.example.nikita.vkapi.data.dataBase

import android.content.Context
import com.example.nikita.vkapi.data.models.NewsModel
import ru.mosobrnadzor.other.utils.SQLBuilder

object DBReposetory  {
    lateinit var db: DataBase

    fun init (context: Context) {
        db = DataBase(context)
    }

    fun insertNews(listNewsModel: List<NewsModel>) {

        val builder = SQLBuilder().insertOrReplaceInto(DBTable.NEWS_TABLE,
                DBFild.ID,
                DBFild.DATA,
                DBFild.CONTENT,
                DBFild.IMAGE).values()
        for (news in listNewsModel) {
            builder.value(news.id, news.date, news.content, news.imagePath)
        }
        db.writableDatabase.execSQL(builder.toString())
    }

    fun getNewsList() : MutableList<NewsModel> {
        val listNewsModel = ArrayList<NewsModel>()
        val builder = SQLBuilder()
                .select()
                .from(DBTable.NEWS_TABLE)
                .orderBy(DBFild.ID)
                .desc()
        val cursor = db.readableDatabase.rawQuery(builder.toString(), null)

        val indexId = cursor.getColumnIndex(DBFild.ID)
        val indexData = cursor.getColumnIndex(DBFild.DATA)
        val indexContent = cursor.getColumnIndex(DBFild.CONTENT)
        val indexImage = cursor.getColumnIndex(DBFild.IMAGE)
        while (cursor.moveToNext()) {
            val news = NewsModel()
            news.id = cursor.getInt(indexId)
            news.date = cursor.getString(indexData)
            news.content = cursor.getString(indexContent)
            news.imagePath = cursor.getString(indexImage)
            listNewsModel.add(news)
        }
        cursor.close()
        return listNewsModel
    }


}