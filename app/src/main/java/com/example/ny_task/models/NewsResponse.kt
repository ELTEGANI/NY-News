package com.example.ny_task.models

data class NewsResponse(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)