package com.example.vegasapp.repository

import com.example.vegasapp.api.VegasRetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketRepository @Inject constructor(){

    suspend fun getTicketsByEmail(token: String, email: String) =
        VegasRetrofitInstance.api.getTickets("Bearer $token", email)
}