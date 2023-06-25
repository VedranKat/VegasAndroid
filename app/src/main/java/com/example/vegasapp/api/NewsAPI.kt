package com.example.vegasapp.api

import com.example.vegasapp.model.NewsResponse
import com.example.vegasapp.util.Constants.Companion.NEWS_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("v2/top-headlines")
    suspend fun getNews(
        @Query("country")
        country: String = "us",
        @Query("category")
        category: String = "sports",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = NEWS_API_KEY
    ): Response<NewsResponse>

}