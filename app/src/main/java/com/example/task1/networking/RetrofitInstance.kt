package com.example.task1.networking

import com.example.task1.utils.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val newsAPI:NewsAPI by lazy {
            retrofit.create(NewsAPI::class.java)
        }
    }
}
