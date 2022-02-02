package com.example.multichoicesquiz.DBHelper

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.multichoicesquizapp.Model.Category
import com.example.multichoicesquizapp.Model.Question
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

class DBHelper(context : Context) : SQLiteAssetHelper(context, DB_NAME, null,  DATABASE_VERSION){
    companion object {
        private var instance : DBHelper? = null
        private val DB_NAME : String = "EDMTQuiz2019.db"
        private val DATABASE_VERSION = 1

        @Synchronized
        fun getInstance(context: Context) : DBHelper{
            if (instance == null){
                instance = DBHelper(context)
            }
            return instance!!
        }

    }

    //get all category
    val allCategories:MutableList<Category>
        @SuppressLint("Range")
        get() {
            val db : SQLiteDatabase= instance!!.writableDatabase
            val cursor : Cursor= db.rawQuery("SELECT * FROM Category", null)
            val categories = ArrayList<Category>()
            if (cursor.moveToFirst()){
                while (!cursor.isAfterLast){
                    val category = Category(cursor.getInt(cursor.getColumnIndex("ID")),
                        cursor.getString(cursor.getColumnIndex("Name")),
                                cursor.getString(cursor.getColumnIndex("Image")))

                    categories.add(category)
                    cursor.moveToNext()
                }
            }
            cursor.close()
            db.close()
            return categories
        }


    //get all question by category
    @SuppressLint("Range")
    fun getAllQuestionByCategory(categoryId: Int) : MutableList<Question>{
        var db : SQLiteDatabase = instance!!.writableDatabase
        var cursor : Cursor = db.rawQuery("SELECT * FROM Question WHERE categoryId = $categoryId ORDER BY RANDOM() LIMIT 30", null)
        val questionlist = ArrayList<Question>()
        if (cursor.moveToFirst()){
            while (!cursor.isAfterLast) {
                val question = Question(cursor.getInt(cursor.getColumnIndex("ID")),
                    cursor.getString(cursor.getColumnIndex("QuestionText")),
                    cursor.getString(cursor.getColumnIndex("QuestionImage")),
                    cursor.getString(cursor.getColumnIndex("AnswerA")),
                    cursor.getString(cursor.getColumnIndex("AnswerB")),
                    cursor.getString(cursor.getColumnIndex("AnswerC")),
                    cursor.getString(cursor.getColumnIndex("AnswerD")),
                    cursor.getString(cursor.getColumnIndex("CorrectAnswer")),
                    if (cursor.getInt(cursor.getColumnIndex("IsImageQuestion")) == 0) java.lang.Boolean.FALSE else java.lang.Boolean.TRUE,
                    cursor.getInt(cursor.getColumnIndex("CategoryID"))
                )
                questionlist.add(question)
                cursor.moveToNext()
            }
        }
        cursor.close()
        db.close()
        return  questionlist
    }

}