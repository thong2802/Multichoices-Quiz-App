package com.example.multichoicesquizapp.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.multichoicesquizapp.QuestionFragment
import java.lang.StringBuilder

class MyFragmentAdapter(
    fm:FragmentManager,
    var context: Context,
    var fragmentList : List<QuestionFragment>
) : FragmentPagerAdapter(fm) {
    internal var instance:MyFragmentAdapter? = null
    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return StringBuilder("Question").append(position+1).toString()
    }
}