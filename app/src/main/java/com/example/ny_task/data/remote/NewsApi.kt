package com.example.ny_task.data.remote

import com.example.ny_task.models.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NewsApi {
    @GET("{section}/{period}.json?api-key=sliRVZn0SQ5ZCzAlvLAnhf1ACyrr5T4X")
    suspend fun getMostViewNews(
        @Path("section") section: String,
        @Path("period") period:String
    ): NewsResponse
}