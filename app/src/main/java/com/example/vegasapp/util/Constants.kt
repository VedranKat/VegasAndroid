package com.example.vegasapp.util

import com.example.vegasapp.BuildConfig

class Constants {
    companion object{
        const val NEWS_API_KEY = BuildConfig.API_KEY
        const val NEWS_API_URL = "https://newsapi.org"
        const val SEARCH_NEWS_TIME_DELAY = 500L
        const val QUERY_PAGE_SIZE = 20
    }
}