package com.example.vegasapp.repository

import com.example.vegasapp.api.NewsRetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor()
{
    suspend fun getNews(country: String, countryCode: String, page: Int) =
        NewsRetrofitInstance.api.getNews(country, countryCode, page)
}