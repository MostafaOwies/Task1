package com.example.task1.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task1.MainActivity
import com.example.task1.adapters.BaseAdapter
import com.example.task1.databinding.FragmentNewsBinding
import com.example.task1.ui.NewsViewModel
import com.example.task1.utils.Constants.QUERY_PAGE_SIZE
import com.example.task1.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private var _binding:FragmentNewsBinding?=null
    private val binding get()=_binding
    lateinit var viewModel: NewsViewModel
    lateinit var baseAdapter: BaseAdapter
    private val coroutineScope = CoroutineScope(Dispatchers.Main.immediate)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setUpRecyclerView()


        coroutineScope.launch {
            viewModel.news.collect { response ->
                when (response) {
                    is Resource.Success -> {
                        hideProgressBar()
                        response.data?.let {
                            baseAdapter.difference.submitList(it.articles.toList())
                            val totalPages = it.totalResults / QUERY_PAGE_SIZE + 2
                            isLastPage = viewModel.newsPage == totalPages
                            if (isLastPage) {
                                binding?.rvNews?.setPadding(0, 0, 0, 0)
                            }
                        }
                    }
                    is Resource.Error -> {
                        hideProgressBar()
                    }
                    is Resource.Loading -> {
                        showProgressBar()
                    }
                    else -> { }
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding?.paginationProgressBar?.visibility=View.INVISIBLE
        isLoading=false
    }
    private fun showProgressBar() {
        binding?.paginationProgressBar?.visibility=View.VISIBLE
        isLoading=true
    }

    var isLoading=false
    var isScrolling=false
    var isLastPage=false
    private val myScrollListener=object :RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage
                    && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                coroutineScope.launch {
                    viewModel.getNews("us")
                    isScrolling = false
                }
            }
        }
    }

    private fun setUpRecyclerView(){
        baseAdapter= BaseAdapter(this)
        binding?.rvNews?.adapter=baseAdapter
        binding?.rvNews?.layoutManager=LinearLayoutManager(activity)
        binding?.rvNews?.addOnScrollListener(this@NewsFragment.myScrollListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
    }
}