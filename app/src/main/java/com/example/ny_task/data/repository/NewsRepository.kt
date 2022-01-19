package com.example.ny_task.data.repository

import com.example.ny_task.data.NewsService
import com.example.ny_task.models.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class NewsRepository @Inject constructor(private val newsService: NewsService){
    suspend fun getNews(units:String) : Flow<Result<NewsResponse>> =
        newsService.fetchNews().map {
            if(it.isSuccess)
                Result.success(it.getOrNull()!!)
            else
                Result.failure(it.exceptionOrNull()!!)
        }

}