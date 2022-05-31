package com.example.task1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.networking.NewsRepoAbstraction
import com.example.task1.newslist.News
import com.example.task1.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsViewModel(val newsRepo: NewsRepoAbstraction):ViewModel(),NewsViewModelAbstraction {

    val news :MutableStateFlow<Resource<News>> = MutableStateFlow(Resource.Empty())
    var newsPage =1
    private var newsResponse:News?=null

    init {
         viewModelScope.launch{ getNews("us")}
    }

    override suspend fun getNews(countryCode:String) = withContext(Dispatchers.Default) {
         news.value=Resource.Loading()
         val response=newsRepo.getNews(countryCode,newsPage)
         news.value=handleNewsResponse( response)
    }

     override fun handleNewsResponse(response: Response<News>):Resource<News>{
        if (response.isSuccessful){
            response.body()?.let {
                newsPage++
                if (newsResponse==null){
                    newsResponse=it
                }else{
                    val oldArticles = newsResponse?.articles
                    val newArticles=it.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(newsResponse?:it)
            }
        }
        return  Resource.Error(response.message(),null)
    }
}