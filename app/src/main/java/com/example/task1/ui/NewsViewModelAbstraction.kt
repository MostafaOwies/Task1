package com.example.task1.ui


interface NewsViewModelAbstraction {
    suspend fun getNews(countryCode:String)
}