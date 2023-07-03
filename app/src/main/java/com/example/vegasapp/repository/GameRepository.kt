package com.example.vegasapp.repository

import com.example.vegasapp.api.VegasRetrofitInstance
import com.example.vegasapp.model.GameResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor()
{
    suspend fun getOdds(token: String): Response<List<GameResponse>>
    {
        return VegasRetrofitInstance.api.getOdds("Bearer $token")
    }
}