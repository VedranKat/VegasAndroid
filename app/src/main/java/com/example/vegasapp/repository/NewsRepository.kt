package com.example.vegasapp.repository

import com.example.vegasapp.api.NewsRetrofitInstance

class NewsRepository()
{
    suspend fun getNews(country: String, countryCode: String, page: Int) =
        NewsRetrofitInstance.api.getNews(country, countryCode, page)
}