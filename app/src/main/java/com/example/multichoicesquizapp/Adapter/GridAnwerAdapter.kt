package com.example.multichoicesquizapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.multichoicesquizapp.Model.CurrentQuestion
import com.example.multichoicesquizapp.R
import com.example.multichoicesquizapp.common.Common

class GridAnwerAdapter(
    internal val context: Context,
    internal val answerSheetList: List<CurrentQuestion>
) :  RecyclerView.Adapter<GridAnwerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            internal var question_item : View
            init {
                question_item = itemView.findViewById(R.id.question_item) as View
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.layout_grid_answer_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       if (answerSheetList[position].type == Common.ANSWER_TYPE.RIGHT_ANSWER){
           holder.question_item.setBackgroundResource(R.drawable.grid_item_right_answer)
       }
       else  if (answerSheetList[position].type == Common.ANSWER_TYPE.WRONG_ANSWER){
           holder.question_item.setBackgroundResource(R.drawable.grid_item_wrong_answer)
       }
       else {
           holder.question_item.setBackgroundResource(R.drawable.grid_item_answer)
       }

    }

    override fun getItemCount(): Int {
        return answerSheetList.size
    }
}