package com.example.task1.dependencyinjection.app

import com.example.task1.dependencyinjection.RetrofitQ
import com.example.task1.networking.NewsAPI
import com.example.task1.networking.UrlProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @AppScope
    @RetrofitQ
    fun retrofit(urlProvider: UrlProvider):Retrofit{
        return Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @AppScope
    fun urlProvider()=UrlProvider()

    @Provides
    @AppScope
    fun newsAPI(@RetrofitQ retrofit: Retrofit) =retrofit.create(NewsAPI::class.java)
}