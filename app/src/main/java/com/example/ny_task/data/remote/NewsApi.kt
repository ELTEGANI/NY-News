package com.example.ny_task.data.remote

import com.example.ny_task.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("{section}/{period}.json?api-key=sliRVZn0SQ5ZCzAlvLAnhf1ACyrr5T4X")
    suspend fun getCurrentWeather(
        @Query("section") section: String,
        @Query("period") period:String
    ): NewsResponse
}