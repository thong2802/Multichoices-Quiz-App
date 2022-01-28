package com.example.multichoicesquizapp.Interface

import com.example.multichoicesquizapp.Model.CurrentQuestion

interface IAnswerSelect {
    fun selectedAnswer() : CurrentQuestion
    fun showCorrectAnwer()
    fun disableAnswer()
    fun resetQuestion()
}