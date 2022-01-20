package com.example.ny_task.viewmodels

import androidx.lifecycle.*
import com.example.ny_task.data.repository.NewsRepository
import com.example.ny_task.models.NewsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@InternalCoroutinesApi
@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel(){

    val loader = MutableLiveData<Boolean>()
    val newsList: MutableLiveData<Result<NewsResponse?>> = MutableLiveData()

    init {
        getNewsFromApi()
    }

    @InternalCoroutinesApi
    fun getNewsFromApi() {
        viewModelScope.launch {
            loader.postValue(true)
            newsRepository.getNews()
                .onEach {
                    loader.postValue(false)
                }
                .collect {
                    newsList.postValue(it)
                }
        }
    }


}