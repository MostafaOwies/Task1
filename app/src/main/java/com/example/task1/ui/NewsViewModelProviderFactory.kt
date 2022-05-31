package com.example.task1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task1.networking.NewsRepoAbstraction

class NewsViewModelProviderFactory(private val newsRepo: NewsRepoAbstraction) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepo = newsRepo) as T
    }
}