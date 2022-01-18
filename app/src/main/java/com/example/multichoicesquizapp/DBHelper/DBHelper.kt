package com.example.multichoicesquizapp.DBHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DBHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteAssetHelper(context, name, factory, version) {
    companion object {
        private const val DB_NAME : String = "EDMTQuiz2019.db"
        private const val DB_VER : Int = 1

        private var  instance : DBHelper? = null



    }
}