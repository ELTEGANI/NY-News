package com.example.ny_task.viewmodels

import com.example.ny_task.data.repository.NewsRepository
import com.example.ny_task.models.NewsResponse
import com.example.ny_task.utils.BaseUnitTes
import com.example.ny_task.utils.captureValues
import com.example.ny_task.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class NewsViewModelTestShould: BaseUnitTes(){

    private val newsRepository : NewsRepository = mock()
    private val newsResponse : NewsResponse= mock()
    private val expected = Result.success(newsResponse)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getNewsFromRepository() = runBlockingTest{
        val viewModel = mockSuccessfulCase()
        viewModel.newsList.getValueForTest()
        Mockito.verify(newsRepository, times(1)).getNews()
    }


    @Test
    fun showSpinnerWhileLoading() = runBlockingTest{
        val viewModel = mockSuccessfulCase()
        viewModel.loader.captureValues{
            viewModel.newsList.getValueForTest()
            Assert.assertEquals(false,values[0])
        }
    }

    @Test
    fun emitsCurrentWeatherFromRepository() = runBlockingTest{
        val viewModel = mockSuccessfulCase()
        assertEquals(expected,viewModel.newsList.getValueForTest())
    }



    @Test
    fun emitErrorWhenReceiveError(){
        val viewModel = mockErrorCase()
        Assert.assertEquals(
            exception,
            viewModel.newsList.getValueForTest()?.exceptionOrNull()
        )
    }

    @Test
    fun closeLoaderAfterCurrentWeatherLoad() = runBlockingTest{
        val viewModel = mockErrorCase()
        viewModel.loader.captureValues{
            viewModel.newsList.getValueForTest()
            Assert.assertEquals(false,values.last())
        }
    }


    private fun mockErrorCase(): NewsViewModel {
        runBlocking {
            whenever(newsRepository.getNews()).thenReturn(
                flow {
                    emit(Result.failure<NewsResponse>(exception))
                }
            )
        }
        return NewsViewModel(newsRepository)
    }


    private fun mockSuccessfulCase(): NewsViewModel {
        runBlocking {
            whenever(newsRepository.getNews()).thenReturn(
                flow {
                    emit(expected)
                }
            )
        }
        return NewsViewModel(newsRepository)
    }

}