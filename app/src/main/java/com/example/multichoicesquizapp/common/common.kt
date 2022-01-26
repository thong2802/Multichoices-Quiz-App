package com.example.multichoicesquizapp.common

import com.example.multichoicesquizapp.Model.Category
import com.example.multichoicesquizapp.Model.CurrentQuestion
import com.example.multichoicesquizapp.Model.Question
import com.example.multichoicesquizapp.QuestionFragment

object common {
    val TOTLAL_TIME = 20*60*1000 // 20 min
    var answerSheetList : MutableList<CurrentQuestion> = ArrayList()
    var questionList : MutableList<Question> = ArrayList()
    var selectedCagory : Category? = null
    var fragmentList : MutableList<QuestionFragment> = ArrayList()

    enum class ANSWER_TYPE {
        NO_ANSWER,
        RIGHT_ANSWER,
        WRONG_ANSWER
    }
}