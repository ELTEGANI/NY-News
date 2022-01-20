package com.example.ny_task.data.service

import com.example.ny_task.data.NewsService
import com.example.ny_task.data.remote.NewsApi
import com.example.ny_task.models.NewsResponse
import com.example.ny_task.utils.BaseUnitTes
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

class NewsServiceTestShould: BaseUnitTes() {

    lateinit var newsService: NewsService
    private val newsApi:NewsApi = mock()
    private val newsResponse:NewsResponse = mock()


    @ExperimentalCoroutinesApi
    @Test
    fun fetchNewsFromApi() = runBlockingTest {
        newsService = NewsService(newsApi)
        newsService.fetchNews().first()
        Mockito.verify(newsApi, Mockito.times(1))
            .getMostViewNews("all-sections","7")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun convertValuesOfResultAndEmitsThem() = runBlockingTest {
        mockSuccessfulCase()
        assertEquals(Result.success(newsResponse),newsService.fetchNews().first())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitErrorResultWhenNetworkFails() = runBlockingTest{
        mockFailerCase()
        Assert.assertEquals("Something went wrong",newsService.fetchNews().first().exceptionOrNull()?.message)
    }

    private suspend fun mockFailerCase() {
        whenever(newsApi.getMostViewNews("all-sections","7")).thenThrow(RuntimeException("Error from Backend"))
        newsService = NewsService(newsApi)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(newsApi.getMostViewNews("all-sections","7")).thenReturn(newsResponse)
        newsService = NewsService(newsApi)
    }


}