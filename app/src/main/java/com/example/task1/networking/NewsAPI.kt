package com.example.task1.networking

import com.example.task1.newslist.News
import com.example.task1.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        countryCode:String="us",
        @Query("page")
        pageNumber :Int=1,
        @Query("apiKey")
        apiKey : String =API_KEY
    ):Response<News>
}