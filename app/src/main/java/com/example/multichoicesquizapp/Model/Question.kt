package com.example.multichoicesquizapp.Model

data class Question(
    var id : Int,
    var questionText : String?,
    var questionImage : String?,
    var answerA : String?,
    var answerB : String?,
    var answerC : String?,
    var answerD : String?,
    var correctAnswer: String?,
    var isImageQuestion : Boolean,
    var categoryID : Int
) {
}