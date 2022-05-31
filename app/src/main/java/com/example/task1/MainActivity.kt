package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.task1.networking.NewsRepo
import com.example.task1.ui.NewsViewModel
import com.example.task1.ui.NewsViewModelProviderFactory
import com.example.task1.ui.fragments.NewsFragment

class MainActivity : AppCompatActivity() {
    lateinit var viewModel:NewsViewModel
    private val newsFragment=NewsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepo=NewsRepo()
        val viewModelProviderFactory=NewsViewModelProviderFactory(newsRepo)
        viewModel= ViewModelProvider(this,viewModelProviderFactory)[NewsViewModel::class.java]


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragFrameLayout,newsFragment)
            commit()
        }
    }
}