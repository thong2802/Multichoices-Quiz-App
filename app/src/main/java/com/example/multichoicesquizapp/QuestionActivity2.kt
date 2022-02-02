package com.example.multichoicesquizapp


import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.multichoicesquiz.DBHelper.DBHelper
import com.example.multichoicesquizapp.Adapter.GridAnwerAdapter
import com.example.multichoicesquizapp.Adapter.MyFragmentAdapter
import com.example.multichoicesquizapp.Model.CurrentQuestion
import com.example.multichoicesquizapp.common.Common
import com.example.multichoicesquizapp.databinding.ActivityQuestion2Binding
import kotlinx.android.synthetic.main.content_question2.*
import java.util.concurrent.TimeUnit

class QuestionActivity2 : AppCompatActivity() {

   lateinit var countDownTimer : CountDownTimer
   var timePlay = Common.TOTLAL_TIME
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityQuestion2Binding
   lateinit var txt_wrong_answer : TextView

    lateinit var adapter : GridAnwerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestion2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarQuestion2.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_question2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //get Question base on CATEGORY
        getQuestion()

        if(Common.questionList.size > 0){
            // show timer, Right anwer, text View
            txt_time.visibility = View.VISIBLE
            txt_right_anwser.visibility = View.VISIBLE
            countTime()

            // gen item fro grid anwer
            genItems()
            grid_answer.setHasFixedSize(true)
            grid_answer.layoutManager = GridLayoutManager(
                this,
                if (Common.questionList.size > 5)
                    Common.questionList.size / 2
                else Common.questionList.size)
            adapter = GridAnwerAdapter(this, Common.answerSheetList)
            grid_answer.adapter = adapter

            // Gen fragament List
            genFragmentList()

            val fragmentAdapter = MyFragmentAdapter(supportFragmentManager, this,Common.fragmentList)
            view_pager.offscreenPageLimit = Common.questionList.size
            view_pager.adapter = fragmentAdapter // Bind Question to View Pager
            slide_tabs.setupWithViewPager(view_pager)


            view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                var SCROLLING_RIGHT = 0
                var SCROLLING_LEFT = 1
                var SCROLLING_UNDETERMINED = 2

                var currentScrollDirection = SCROLLING_UNDETERMINED

                private val isScrollDirectionUndetermined:Boolean
                    get() = currentScrollDirection == SCROLLING_UNDETERMINED
                private val isScrollDirectionRight:Boolean
                    get() = currentScrollDirection == SCROLLING_RIGHT
                private val isScrollDirectionLeft:Boolean
                    get() = currentScrollDirection == SCROLLING_LEFT

                private fun setScrollingDirection(positionOffset : Float){
                    if (1-positionOffset >= 0.5){
                        this.currentScrollDirection = SCROLLING_RIGHT
                    }else if (1- positionOffset <= 0.5){
                        this.currentScrollDirection = SCROLLING_LEFT
                    }
                }
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                        if (isScrollDirectionUndetermined)
                            setScrollingDirection(positionOffset)
                }
                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager.SCROLL_STATE_IDLE){
                        this.currentScrollDirection = SCROLLING_UNDETERMINED
                    }
                }
                override fun onPageSelected(p0: Int) {
                    val questionFragment : QuestionFragment
                    var position = 0;
                    if (p0 > 0){
                        if (isScrollDirectionRight){
                            questionFragment = Common.fragmentList[p0-1]
                            position = p0 - 1
                        }else if (isScrollDirectionLeft){
                            questionFragment = Common.fragmentList[p0+1]
                            position = p0 + 1
                        }
                        else {
                            questionFragment = Common.fragmentList[p0]
                        }
                    }
                    else
                    {
                        questionFragment = Common.fragmentList[0]
                        position = 0
                    }

                    if (Common.answerSheetList[position].type == Common.ANSWER_TYPE.NO_ANSWER)
                   {
                        //show correct answer
                        val question_state = questionFragment.selectedAnswer()
                        Common.answerSheetList[position] = question_state
                        adapter.notifyDataSetChanged()

                        countCorrectAnswer()
                        txt_right_anwser.text = ("${Common.right_answer_count} / ${Common.questionList.size}")
                         txt_wrong_answer.text = "${Common.wrong_answer_count}"

                        if (question_state.type != Common.ANSWER_TYPE.WRONG_ANSWER){
                            questionFragment.showCorrectAnwer()
                            questionFragment.disableAnswer()
                        }
                    }
                }
            })

        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item  = menu!!.findItem(R.id.menu_wrong_answer)
        val layout  = item.actionView as ConstraintLayout
        txt_wrong_answer = layout.findViewById(R.id.txt_wrong_answer) as TextView
        txt_wrong_answer.text = 0.toString()
        return true
    }

    private fun countCorrectAnswer() {
       Common.right_answer_count = 0
        Common.wrong_answer_count = 0

        for(item in Common.answerSheetList) {
            if (item.type == Common.ANSWER_TYPE.RIGHT_ANSWER){
                Common.right_answer_count++
            }else if(item.type == Common.ANSWER_TYPE.WRONG_ANSWER){
                Common.wrong_answer_count++
            }
        }
    }

    private fun genFragmentList() {
        for (i in Common.questionList.indices){
            val bundle = Bundle()
            bundle.putInt("index", i)
            var fragment = QuestionFragment()
            fragment.arguments = bundle
            Common.fragmentList.add(fragment)
        }
    }

    private fun genItems() {
        for (i in Common.questionList.indices) {
            Common.answerSheetList.add(
                CurrentQuestion(
                    i,
                    Common.ANSWER_TYPE.NO_ANSWER
                )
            ) // No answer for all question
        }

    }

    private fun countTime() {
        countDownTimer = object:CountDownTimer(Common.TOTLAL_TIME.toLong(), 1000){
            override fun onTick(interval: Long) {
                txt_time.text = (java.lang.String.format("%2d:%2d",
                    TimeUnit.MILLISECONDS.toMinutes(interval),
                    TimeUnit.MILLISECONDS.toSeconds(interval) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(interval))))
                    timePlay -= 1000
            }

            override fun onFinish() {
                finishGame()
            }

        }
    }

    private fun finishGame() {
        var position = view_pager.currentItem
        var questionFragment = Common.fragmentList[position]

        val question_state = questionFragment.selectedAnswer()
        Common.answerSheetList[position] = question_state
        adapter.notifyDataSetChanged()

        countCorrectAnswer()
        txt_right_anwser.text = ("${Common.right_answer_count} / ${Common.questionList.size}")
        txt_wrong_answer.text = "${Common.wrong_answer_count}"

        if (question_state.type != Common.ANSWER_TYPE.WRONG_ANSWER){
            questionFragment.showCorrectAnwer()
            questionFragment.disableAnswer()

        }
    }

    private fun getQuestion() {
        Common.questionList = DBHelper.getInstance(this)
            .getAllQuestionByCategory(Common.selectedCagory!!.id)

        if (Common.questionList.size == 0){
//            MaterialAlertDialogBuilder(this)
//                .setMessage("This is a test of MaterialAlertDialogBuilder")
//                .setPositiveButton("Ok", null)
//                .show()
            Toast.makeText(this, "Can not question", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.question_activity2, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_question2)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}