package com.example.multichoicesquizapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.multichoicesquizapp.Interface.IAnswerSelect
import com.example.multichoicesquizapp.Model.CurrentQuestion
import com.example.multichoicesquizapp.Model.Question
import com.example.multichoicesquizapp.common.Common
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_question.*
import java.lang.Exception
import java.lang.StringBuilder

class QuestionFragment : Fragment(), IAnswerSelect {
    lateinit var txt_question_text : TextView
    lateinit var cbA : CheckBox
    lateinit var cbB : CheckBox
    lateinit var cbC : CheckBox
    lateinit var cbD : CheckBox

    lateinit var frame_image : FrameLayout
    lateinit var progress_bar : ProgressBar
    var question:Question? = null
    var questionIndex = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var itemView : View=  inflater.inflate(R.layout.fragment_question, container, false)

        questionIndex = requireArguments().getInt("index", -1)
        frame_image = itemView.findViewById(R.id.frame_image) as FrameLayout
        question = Common.questionList[questionIndex]
        if (question != null){
            progress_bar = itemView.findViewById(R.id.progress_bar) as ProgressBar
            if (question!!.isImageQuestion) {
                val imageView = itemView.findViewById<View>(R.id.img_question) as ImageView

                // getImage
                Picasso.get().load(question!!.questionImage).into(imageView,object:Callback {
                    override fun onSuccess() {
                            progress_bar.visibility = View.GONE
                    }
                    override fun onError(e: Exception?) {
                        img_question.setImageResource(R.drawable.ic_baseline_error_outline_24)
                    }

                })
            }else {
                frame_image.visibility = View.GONE
            }
            txt_question_text  = itemView.findViewById(R.id.txt_question_text) as TextView
            txt_question_text.text = question!!.questionText

            cbA = itemView.findViewById(R.id.cbA) as CheckBox
            cbA.text = question!!.answerA

            cbB = itemView.findViewById(R.id.cbB) as CheckBox
            cbB.text = question!!.answerB

            cbC = itemView.findViewById(R.id.cbC) as CheckBox
            cbC.text = question!!.answerC

            cbD = itemView.findViewById(R.id.cbD) as CheckBox
            cbD.text = question!!.answerD

            // EVEN
            cbA.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    Common.selected_values.add(cbA.text.toString())
                }else {
                    Common.selected_values.remove(cbA.text.toString())
                }
            }

            cbB.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    Common.selected_values.add(cbB.text.toString())
                }else {
                    Common.selected_values.remove(cbB.text.toString())
                }
            }

            cbC.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    Common.selected_values.add(cbC.text.toString())
                }else {
                    Common.selected_values.remove(cbC.text.toString())
                }
            }

            cbD.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    Common.selected_values.add(cbD.text.toString())
                }else {
                    Common.selected_values.remove(cbD.text.toString())
                }
            }


        }
        return itemView
    }

    override fun selectedAnswer(): CurrentQuestion {
        // REMOVE ALL duplicate item in Select_answer
        Common.selected_values.distinct()

        if (Common.answerSheetList[questionIndex].type == Common.ANSWER_TYPE.NO_ANSWER){
            val currentQuestion = CurrentQuestion(questionIndex, Common.ANSWER_TYPE.NO_ANSWER)
            var result = StringBuilder()
            if (Common.selected_values.size > 1){ // if multiple choose
                  var arrayAnswer = Common.selected_values.toTypedArray()
                // here we will get the first charater answer
                // EX : array[0] = A. Paris
                // EX : array[1] = B. NewYork
                //Correct Answer = A,B
                // So this code will get 'A', 'B' in array for us
                for (i in arrayAnswer!!.indices){
                    if (i < arrayAnswer!!.size - 1){
                        result.append(StringBuilder((arrayAnswer!![i] as String).substring(0,1)).append(","))
                    }else {
                        result.append((arrayAnswer!![i] as String).substring(0,1))
                    }
                }
            }else if (Common.selected_values.size == 1) { // only one answer
                var arrayAnswer = Common.selected_values.toTypedArray()
                result.append((arrayAnswer!![0] as String).substring(0,1))
            }
            if (question != null) {
               if (!TextUtils.isEmpty(result)){
                   if (result.toString() == question!!.correctAnswer){
                       currentQuestion.type = Common.ANSWER_TYPE.RIGHT_ANSWER
                   }else {
                       currentQuestion.type = Common.ANSWER_TYPE.WRONG_ANSWER
                   }
               }else {
                   currentQuestion.type = Common.ANSWER_TYPE.NO_ANSWER
               }
            }else {
                Toast.makeText(activity, "Can not get question", Toast.LENGTH_SHORT).show()
                currentQuestion.type = Common.ANSWER_TYPE.NO_ANSWER
            }

            Common.selected_values.clear() // clear select array
            return currentQuestion
        }else {
            return Common.answerSheetList[questionIndex]
        }
    }

    override fun showCorrectAnwer() {
        var correctAnswers = question!!.correctAnswer!!.split(",".toRegex())
            .dropLastWhile { it.isEmpty() }
        for (answer: String in correctAnswers) {
            if (answer.equals("A")) {
                cbA.setTypeface(null, Typeface.BOLD)
                cbA.setTextColor(Color.RED)
            } else if (answer.equals("B")) {
                cbB.setTypeface(null, Typeface.BOLD)
                cbB.setTextColor(Color.RED)
            } else if (answer.equals("C")) {
                cbC.setTypeface(null, Typeface.BOLD)
                cbC.setTextColor(Color.RED)
            } else if (answer.equals("D")) {
                cbD.setTypeface(null, Typeface.BOLD)
                cbD.setTextColor(Color.RED)
            }
        }
    }

    override fun disableAnswer() {
        cbA.isEnabled = false
        cbB.isEnabled = false
        cbC.isEnabled = false
        cbD.isEnabled = false
    }

    override fun resetQuestion() {
        cbA.isEnabled = true
        cbB.isEnabled = true
        cbC.isEnabled = true
        cbD.isEnabled = true

        cbA.isChecked = false
        cbB.isChecked = false
        cbC.isChecked = false
        cbD.isChecked = false

        cbA.setTypeface(null, Typeface.NORMAL)
        cbA.setTextColor(Color.BLACK)

        cbB.setTypeface(null, Typeface.NORMAL)
        cbB.setTextColor(Color.BLACK)

        cbC.setTypeface(null, Typeface.NORMAL)
        cbC.setTextColor(Color.BLACK)

        cbD.setTypeface(null, Typeface.NORMAL)
        cbD.setTextColor(Color.BLACK)

        Common.selected_values.clear()
    }

}