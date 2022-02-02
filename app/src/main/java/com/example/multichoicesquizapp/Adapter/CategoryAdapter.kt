package com.example.multichoicesquiz.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.multichoicesquizapp.Interface.IOnRecyclerViewItemClickListener
import com.example.multichoicesquizapp.Model.Category
import com.example.multichoicesquizapp.QuestionActivity2
import com.example.multichoicesquizapp.R
import com.example.multichoicesquizapp.common.Common

class CategoryAdapter(
    internal var context : Context,
    internal var categoryList: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal  var txt_category_name : TextView
        internal  var cart_category:CardView
        internal lateinit var iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener

        fun setiOnRecyclerViewItemClickListener(iOnRecyclerViewItemClickListener: IOnRecyclerViewItemClickListener){
            this.iOnRecyclerViewItemClickListener = iOnRecyclerViewItemClickListener
        }
        init {
            txt_category_name = itemView.findViewById(R.id.txt_category_name) as TextView
            cart_category    = itemView.findViewById(R.id.card_category) as CardView
            itemView.setOnClickListener(this)
        }
        override fun onClick(view: View?) {
            if (view != null) {
                iOnRecyclerViewItemClickListener.OnClick(view, adapterPosition)
            }

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false)
        return MyViewHolder(itemView);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_category_name.text = categoryList[position].name
        holder.setiOnRecyclerViewItemClickListener(object :IOnRecyclerViewItemClickListener{
            override fun OnClick(view: View, position: Int) {
              Common.selectedCagory = categoryList[position]
              val intent = Intent(context, QuestionActivity2::class.java)
              context.startActivity(intent)
            }
        })
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}