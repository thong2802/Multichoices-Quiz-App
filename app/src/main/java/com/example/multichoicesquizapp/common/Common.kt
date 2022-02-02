package com.example.multichoicesquizapp.common

import com.example.multichoicesquizapp.Model.Category
import com.example.multichoicesquizapp.Model.CurrentQuestion
import com.example.multichoicesquizapp.Model.Question
import com.example.multichoicesquizapp.QuestionFragment
import java.lang.StringBuilder

object Common {
    val TOTLAL_TIME = 20*60*1000 // 20 min
    var answerSheetList : MutableList<CurrentQuestion> = ArrayList()
    var questionList : MutableList<Question> = ArrayList()
    var selectedCagory : Category? = null
    var fragmentList : MutableList<QuestionFragment> = ArrayList()
    var selected_values : MutableList<String> = ArrayList()

    var timer = 0;
    var right_answer_count = 0
    var wrong_answer_count = 0
    var no_answer_count = 0
    var data_question = StringBuilder()



    enum class ANSWER_TYPE {
        NO_ANSWER,
        RIGHT_ANSWER,
        WRONG_ANSWER
    }
}