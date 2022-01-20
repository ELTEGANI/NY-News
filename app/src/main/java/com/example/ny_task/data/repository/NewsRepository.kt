package com.example.ny_task.data.repository

import com.example.ny_task.data.NewsService
import com.example.ny_task.models.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class NewsRepository @Inject constructor(private val newsService: NewsService){
    suspend fun getNews() : Flow<Result<NewsResponse?>> =
        newsService.fetchNews().map {
            (if(it.isSuccess)
                Result.success(it.getOrNull())
            else
                it.exceptionOrNull()?.let {
                        it1 -> Result.failure(it1)
                }) as Result<NewsResponse?>
        }

}