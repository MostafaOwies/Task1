package com.example.task1

import android.os.Bundle
import androidx.activity.viewModels
import com.example.task1.ui.NewsViewModel
import com.example.task1.ui.fragments.NewsFragment
import com.example.task1.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
     val viewModel:NewsViewModel by viewModels()
    private val newsFragment=NewsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragFrameLayout,newsFragment)
            commit()
        }
    }
}