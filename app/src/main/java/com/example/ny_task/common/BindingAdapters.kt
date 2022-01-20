package com.example.ny_task.common

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.ny_task.models.NewsResponse
import com.example.ny_task.models.Result


@SuppressLint("SetTextI18n")
@BindingAdapter("title")
fun TextView.setTitleTextView(result: Result) {
    result.let {
        if(result.title.length >= 30){
            text = result.title.substring(0,30)+ "..."
        }else{
            text = result.title
        }
    }
}


@SuppressLint("SetTextI18n")
@BindingAdapter("abstract")
fun TextView.setAbstractTextView(result: Result) {
    result.let {
        if(result.title.length >= 50){
            text = result.title.substring(0,40)+ "..."
        }else{
            text = result.title
        }
    }
}