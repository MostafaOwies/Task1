package com.example.task1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.networking.NewsRepoAbstraction
import com.example.task1.newslist.News
import com.example.task1.newslist.NewsListUseCase
import com.example.task1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    private val newsRepo: NewsRepoAbstraction,
    private val newsListUseCase: NewsListUseCase
    ):ViewModel(),NewsViewModelAbstraction {

    private val _news= MutableStateFlow<Resource<News>>(Resource.Empty())
    val news :StateFlow<Resource<News>> = _news

    init {
         viewModelScope.launch{
             getNews("us")
         }
    }

    override suspend fun getNews(countryCode:String) = withContext(Dispatchers.Default) {
        try {
            _news.value = Resource.Loading()
            _news.value = newsListUseCase.handleNewsResponse(newsRepo.getNews(countryCode,newsListUseCase.newsPage))
        }catch (t :Throwable ){
            throw t
        }
    }
}