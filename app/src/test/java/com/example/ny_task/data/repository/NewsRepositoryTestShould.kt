package com.example.ny_task.data.repository

import com.example.ny_task.data.NewsService
import com.example.ny_task.models.NewsResponse
import com.example.ny_task.utils.BaseUnitTes
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.verify


class NewsRepositoryTestShould : BaseUnitTes(){

    private val newsService:NewsService = mock()
    private val newsResponse:NewsResponse = mock()
    private val exception = RuntimeException("Something went wrong")


    @ExperimentalCoroutinesApi
    @Test
    fun getNewsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        repository.getNews()
        verify(newsService, times(1)).fetchNews()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun emitNewsFromService() = runBlockingTest {
        val repository = mockSuccessfulCase()
        assertEquals(newsResponse,repository.getNews().first().getOrNull())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun propagateErrors() = runBlockingTest {
        val repository = mockFailure()
        Assert.assertEquals(exception,repository.getNews().first().exceptionOrNull())
    }

    private suspend fun mockFailure(): NewsRepository {
        whenever(newsService.fetchNews()).thenReturn(
            flow {
                emit(Result.failure<NewsResponse>(exception))
            }
        )
        return NewsRepository(newsService)
    }

    private suspend fun mockSuccessfulCase(): NewsRepository {
        whenever(newsService.fetchNews()).thenReturn(
            flow {
                emit(Result.success(newsResponse))
            }
        )
        return NewsRepository(newsService)
    }

}