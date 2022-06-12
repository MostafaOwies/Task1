package com.example.task1.newslist

import com.example.task1.utils.Resource
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NewsListUseCase @Inject constructor() {

    var newsPage =1
    private var newsResponse:News?=null
     suspend fun handleNewsResponse(response: Response<News>): Resource<News> {
        return withContext(Dispatchers.Default) {
            try {

                if (response.isSuccessful) {
                    response.body()?.let {
                        newsPage++
                        if (newsResponse == null) {
                            newsResponse = it
                        } else {
                            val oldArticles = newsResponse?.articles
                            val newArticles = it.articles
                            oldArticles?.addAll(newArticles)
                        }
                        return@withContext Resource.Success(newsResponse ?: it)
                    }
                }
                return@withContext Resource.Error(response.message(), null)
            }catch (t:Throwable){
                if (t !is CancellationException){
                    return@withContext Resource.Error(response.message(), null)
                }
                else{
                    throw t
                }
            }
        }
    }
}