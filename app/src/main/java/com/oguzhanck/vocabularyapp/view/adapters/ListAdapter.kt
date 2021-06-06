package com.oguzhanck.vocabularyapp.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oguzhanck.vocabularyapp.R
import com.oguzhanck.vocabularyapp.model.ListEntity

class ListAdapter(
    private val wordList: List<ListEntity>,
    private val onClickListenerWordList: OnClickListenerWordList
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    // For the go word details
    interface OnClickListenerWordList {
        fun onItemClick(word: ListEntity)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_list_row_design, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListenerWordList.onItemClick(wordList[position])
        }

        return holder.bind(wordList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var wordListName: TextView = itemView.findViewById(R.id.rowWordTextView)

        fun bind(foodListBind: ListEntity) {
            wordListName.text = foodListBind.word
        }
    }
}