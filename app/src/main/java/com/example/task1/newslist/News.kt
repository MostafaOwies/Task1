package com.example.task1.newslist

data class News(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)