package com.example.multichoicesquizapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.multichoicesquiz.DBHelper.DBHelper
import com.example.multichoicesquizapp.Adapter.GridAnwerAdapter
import com.example.multichoicesquizapp.Adapter.MyFragmentAdapter
import com.example.multichoicesquizapp.Model.CurrentQuestion
import com.example.multichoicesquizapp.common.common
import com.example.multichoicesquizapp.databinding.ActivityQuestion2Binding
import com.google.android.material.datepicker.MaterialStyledDatePickerDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.content_question2.*
import java.util.concurrent.TimeUnit

class QuestionActivity2 : AppCompatActivity() {

   lateinit var countDownTimer : CountDownTimer
   var timePlay = common.TOTLAL_TIME
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityQuestion2Binding

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

        if(common.questionList.size > 0){
            // show timer, Right anwer, text View
            txt_time.visibility = View.VISIBLE
            txt_right_anwser.visibility = View.VISIBLE
            countTime()

            // gen item fro grid anwer
            genItems()
            grid_answer.setHasFixedSize(true)
            grid_answer.layoutManager = GridLayoutManager(
                this,
                if (common.questionList.size > 5)
                    common.questionList.size / 2
                else common.questionList.size)
            adapter = GridAnwerAdapter(this, common.answerSheetList)
            grid_answer.adapter = adapter

            // Gen fragament List
            genFragmentList()

            val fragmentAdapter = MyFragmentAdapter(supportFragmentManager, this,common.fragmentList)
            view_pager.offscreenPageLimit = common.questionList.size
            view_pager.adapter = fragmentAdapter // Bind Question to View Pager
            slide_tabs.setupWithViewPager(view_pager)
        }
    }

    private fun genFragmentList() {
        for (i in common.questionList.indices){
            val bundle = Bundle()
            bundle.putInt("index", i)
            var fragment = QuestionFragment()
            fragment.arguments = bundle
            common.fragmentList.add(fragment)
        }
    }

    private fun genItems() {
        for (i in common.questionList.indices) {
            common.answerSheetList.add(
                CurrentQuestion(
                    i,
                    common.ANSWER_TYPE.NO_ANSWER
                )
            ) // No answer for all question
        }

    }

    private fun countTime() {
        countDownTimer = object:CountDownTimer(common.TOTLAL_TIME.toLong(), 1000){
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
        // code late
    }

    private fun getQuestion() {
        common.questionList = DBHelper.getInstance(this)
            .getAllQuestionByCategory(common.selectedCagory!!.id)

        if (common.questionList.size == 0){
            MaterialAlertDialogBuilder(this)
                // Add customization options here
                .setTitle("Oppps")
                .setIcon(R.drawable.ic_oppps)
                .setMessage("We don't have any question for this ${common.selectedCagory!!.name} category")
                .setPositiveButton("Oke", null)
                .show()
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