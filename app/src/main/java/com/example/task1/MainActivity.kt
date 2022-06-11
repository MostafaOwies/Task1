package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.task1.networking.NewsRepo
import com.example.task1.ui.NewsViewModel
import com.example.task1.ui.NewsViewModelProviderFactory
import com.example.task1.ui.fragments.NewsFragment
import com.example.task1.utils.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {
    lateinit var viewModel:NewsViewModel
    private val newsFragment=NewsFragment()
    @Inject lateinit var myViewModelFactory: NewsViewModelProviderFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        injector.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val newsRepo=NewsRepo()
        //val viewModelProviderFactory=NewsViewModelProviderFactory(newsRepo)
        viewModel= ViewModelProvider(this,myViewModelFactory)[NewsViewModel::class.java]


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragFrameLayout,newsFragment)
            commit()
        }
    }
}