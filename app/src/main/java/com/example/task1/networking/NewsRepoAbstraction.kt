package com.example.task1.networking

import com.example.task1.newslist.News
import retrofit2.Response

interface NewsRepoAbstraction {
    suspend fun getNews(countryCode:String,pageNumber:Int): Response<News>
}