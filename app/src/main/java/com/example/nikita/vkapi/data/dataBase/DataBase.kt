package com.example.nikita.vkapi.data.dataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.mosobrnadzor.other.utils.SQLBuilder

class DataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        createNewTable(db)
    }

    private fun createNewTable(db: SQLiteDatabase) {
        val builder = SQLBuilder()
                .createTableStart(DBTable.NEWS_TABLE)
                .addTableColumn(DBFild.ID, "integer primary key")
                .addTableColumn(DBFild.DATA, "text")
                .addTableColumn(DBFild.CONTENT, "text")
                .addTableColumn(DBFild.IMAGE, "text")
                .endTableColumn()
        db.execSQL(builder.toString())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    companion object {
        const val VERSION = 20180619
        const val NAME = "name"
    }

}