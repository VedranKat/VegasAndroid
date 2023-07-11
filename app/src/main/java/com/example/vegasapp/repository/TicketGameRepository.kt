package com.example.vegasapp.repository

import com.example.vegasapp.api.VegasRetrofitInstance
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TicketGameRepository @Inject constructor(){

    suspend fun getTicketGames(token: String, id: Long) =
        VegasRetrofitInstance.api.getTicketGames("Bearer $token", id)
}