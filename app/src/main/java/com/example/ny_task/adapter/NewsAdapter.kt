package com.example.ny_task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ny_task.databinding.NewsItemListBinding
import com.example.ny_task.models.NewsResponse
import com.example.ny_task.models.Result


class NewsAdapter(private val onClickListener: OnClickListener):  ListAdapter<Result, NewsAdapter.ViewHolder>(
    NewsDiffCallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position:Int){
        holder.bind(getItem(position),onClickListener)
    }


    class ViewHolder private constructor(private var newsItemListBinding: NewsItemListBinding):
        RecyclerView.ViewHolder(newsItemListBinding.root) {
        fun bind(
            result: Result,
            onClickListener: OnClickListener
        ) {
            newsItemListBinding.news = result
            newsItemListBinding.clickListener = onClickListener
            newsItemListBinding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsItemListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }
}


class NewsDiffCallback: DiffUtil.ItemCallback<Result>(){
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem.abstract == newItem.abstract
    }
    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }
}


class OnClickListener(val onClickListener:(result:Result)->Unit){
    fun onClick(result:Result) = onClickListener(result)
}