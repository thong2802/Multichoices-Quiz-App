package com.example.multichoicesquiz.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.multichoicesquizapp.Interface.IOnRecyclerViewItemClickListener
import com.example.multichoicesquizapp.Model.Category
import com.example.multichoicesquizapp.R

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
                Toast.makeText(context, "Click on" + categoryList[position].name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}