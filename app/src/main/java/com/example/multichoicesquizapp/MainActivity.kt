package com.example.multichoicesquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.multichoicesquiz.DBHelper.DBHelper
import com.example.multichoicesquiz.adapter.CategoryAdapter
import com.example.multichoicesquizapp.common.SplaceItemDecorator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Multichoices Quiz"
        setSupportActionBar(toolbar)

        recyclerview_category.setHasFixedSize(true)
        recyclerview_category.layoutManager = GridLayoutManager(this, 2)

        var adapter = CategoryAdapter(this, DBHelper.getInstance(this).allCategories)
       recyclerview_category.addItemDecoration(SplaceItemDecorator(4))
        recyclerview_category.adapter = adapter
    }
}