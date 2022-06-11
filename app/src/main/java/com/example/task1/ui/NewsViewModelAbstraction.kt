package com.example.task1.ui

import com.example.task1.newslist.News
import com.example.task1.utils.Resource
import retrofit2.Response

interface NewsViewModelAbstraction {
    suspend fun getNews(countryCode:String)
    // suspend fun handleNewsResponse(response: Response<News>): Resource<News>
}