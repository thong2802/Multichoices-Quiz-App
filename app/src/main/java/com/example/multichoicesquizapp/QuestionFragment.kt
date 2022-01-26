package com.example.multichoicesquizapp

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.multichoicesquizapp.Model.Question
import com.example.multichoicesquizapp.common.common
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_question.*
import java.lang.Exception

class QuestionFragment : Fragment() {
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
        question = common.questionList[questionIndex]
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

        }
        return itemView
    }


}