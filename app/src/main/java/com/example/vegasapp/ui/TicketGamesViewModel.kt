package com.example.vegasapp.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vegasapp.model.TicketGameResponse
import com.example.vegasapp.repository.TicketGameRepository
import com.example.vegasapp.util.Resource
import com.example.vegasapp.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TicketGamesViewModel @Inject constructor(
    val ticketGameRepository : TicketGameRepository,
    @ApplicationContext val context: Context
): ViewModel(){

    val ticketGames: MutableLiveData<Resource<List<TicketGameResponse>>> = MutableLiveData()
    val _ticketId: MutableLiveData<Int> = MutableLiveData()

    init {
        val token = TokenManager.getToken(context)

        _ticketId.observeForever { id ->
            id?.let {
                getTicketGames(token!!, it.toLong())
            }
        }
    }

    fun setTicketId(id: Int){
        _ticketId.value = id
    }

    private fun getTicketGames(token: String, ticketId: Long) = viewModelScope.launch{
        ticketGames.postValue(Resource.Loading())
        val response = ticketGameRepository.getTicketGames(token, ticketId)
        ticketGames.postValue(handleTicketGamesResponse(response))
    }

    private fun handleTicketGamesResponse(response: Response<List<TicketGameResponse>>): Resource<List<TicketGameResponse>>? {
        if(response.isSuccessful){
            val ticketGameResponse = response.body()
            ticketGameResponse?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}