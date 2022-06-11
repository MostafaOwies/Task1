package com.example.task1.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.task1.networking.NewsRepoAbstraction
import javax.inject.Inject
import javax.inject.Provider

class NewsViewModelProviderFactory @Inject constructor(
   // private val newsRepo: NewsRepoAbstraction,
    private val myViewModelProvider:Provider<NewsViewModel>
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return myViewModelProvider.get() as T
    }
}