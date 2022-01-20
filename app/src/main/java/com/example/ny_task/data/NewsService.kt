package com.example.ny_task.data

import android.util.Log
import com.example.ny_task.data.remote.NewsApi
import com.example.ny_task.models.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class NewsService @Inject constructor(private val newsApi: NewsApi){
    suspend fun fetchNews(): Flow<Result<NewsResponse?>> {
        return flow {
            emit(Result.success(newsApi.getMostViewNews("all-sections","7")))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}