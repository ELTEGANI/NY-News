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
import com.example.ny_task.models.Result as NewsResult

@InternalCoroutinesApi
@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel(){

    val loader = MutableLiveData<Boolean>()
    val newsList: MutableLiveData<Result<NewsResponse?>> = MutableLiveData()

    private val _navigateToSelectedNews = MutableLiveData<NewsResult?>()
    val navigateToSelectedNews: MutableLiveData<NewsResult?>
        get() = _navigateToSelectedNews

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


    fun displayNewsDetails(result:NewsResult) {
        _navigateToSelectedNews.value = result
    }

    fun displayNewsDetailsComplete() {
        _navigateToSelectedNews.value = null
    }

}