package com.example.vegasapp.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vegasapp.model.TicketResponse
import com.example.vegasapp.repository.TicketRepository
import com.example.vegasapp.util.Resource
import com.example.vegasapp.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel @Inject constructor(
    val ticketRepository: TicketRepository,
    @ApplicationContext val context: Context // bandaid
): ViewModel() {

    val tickets : MutableLiveData<Resource<List<TicketResponse>>> = MutableLiveData()

    init {
        initializeTickets()
    }

    private fun initializeTickets() {
        val token = TokenManager.getToken(context)
        val email = TokenManager.getSub(context)
        getTickets(token!!, email!!)
    }

    private fun getTickets(token: String, email: String) = viewModelScope.launch {
        tickets.postValue(Resource.Loading())
        val response = ticketRepository.getTicketsByEmail(token, email)
        tickets.postValue(handleTicketsResponse(response))
    }

    private fun handleTicketsResponse(response: Response<List<TicketResponse>>): Resource<List<TicketResponse>>? {
        if (response.isSuccessful) {
            val ticketResponse = response.body()
            ticketResponse?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun refreshTickets() = viewModelScope.launch {
        initializeTickets()
    }
}