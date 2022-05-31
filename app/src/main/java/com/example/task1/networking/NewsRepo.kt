package com.example.task1.networking

import com.example.task1.newslist.News
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsRepo:NewsRepoAbstraction {

    override suspend fun getNews(countryCode:String, pageNumber:Int): Response<News> = withContext(Dispatchers.Default){
        RetrofitInstance.newsAPI.getNews(countryCode,pageNumber)
    }

}