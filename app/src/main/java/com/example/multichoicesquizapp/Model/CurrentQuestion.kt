package com.example.multichoicesquizapp.Model

import com.example.multichoicesquizapp.common.Common

data class CurrentQuestion(
    val questionIndex : Int,
    var type : Common.ANSWER_TYPE
)
