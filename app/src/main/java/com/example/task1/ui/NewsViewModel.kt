package com.example.task1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.networking.NewsRepoAbstraction
import com.example.task1.newslist.News
import com.example.task1.newslist.NewsListUseCase
import com.example.task1.utils.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    val newsRepo: NewsRepoAbstraction,
    private val newsListUseCase: NewsListUseCase
    ):ViewModel(),NewsViewModelAbstraction {

    private val _news= MutableStateFlow<Resource<News>>(Resource.Empty())
    val news :StateFlow<Resource<News>> = _news
    var newsPage =1
    private var newsResponse:News?=null

    init {
         viewModelScope.launch{ getNews("us")}
    }

    override suspend fun getNews(countryCode:String) = withContext(Dispatchers.Default) {
        try {
            _news.value = Resource.Loading()
            _news.value = newsListUseCase.handleNewsResponse(newsRepo.getNews(countryCode, newsPage))
        }catch (t :Throwable ){
            throw t
        }
    }

    /*
     override suspend fun handleNewsResponse(response: Response<News>):Resource<News> {
         return withContext(Dispatchers.Default) {
             try {
                 if (response.isSuccessful) {
                     response.body()?.let {
                         newsPage++
                         if (newsResponse == null) {
                             newsResponse = it
                         } else {
                             val oldArticles = newsResponse?.articles
                             val newArticles = it.articles
                             oldArticles?.addAll(newArticles)
                         }
                         return@withContext Resource.Success(newsResponse ?: it)
                     }
                 }
                 return@withContext Resource.Error(response.message(), null)
             }catch (t:Throwable){
                 if (t !is CancellationException){
                     return@withContext Resource.Error(response.message(), null)
                 }
                 else{
                     throw t
                 }
             }
         }
     }
    */
}