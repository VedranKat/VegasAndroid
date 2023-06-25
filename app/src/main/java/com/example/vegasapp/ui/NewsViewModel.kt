package com.example.vegasapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vegasapp.model.NewsResponse
import com.example.vegasapp.repository.NewsRepository
import com.example.vegasapp.util.Resource
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    val newsRepository: NewsRepository
) : ViewModel() {

    val news: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var newsPage = 1
    var newsResponse: NewsResponse? = null

    init {
        getNews("us", "sports", 1)
    }

    fun getNews(country: String, category: String, page: Int) = viewModelScope.launch{
        news.postValue(Resource.Loading())
        val response = newsRepository.getNews(country, category, page)
        news.postValue(handleNewsResponse(response))
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse->
                newsPage++
                if(newsResponse==null){
                    newsResponse = resultResponse
                }else{
                    val oldArticles = newsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(newsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}