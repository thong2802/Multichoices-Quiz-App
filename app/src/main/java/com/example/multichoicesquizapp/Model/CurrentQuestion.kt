package com.example.multichoicesquizapp.Model

import com.example.multichoicesquizapp.common.common

data class CurrentQuestion(
    val questionIndex : Int,
    var type : common.ANSWER_TYPE
)
