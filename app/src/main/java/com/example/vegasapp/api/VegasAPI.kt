package com.example.vegasapp.api

import com.example.vegasapp.model.authentication.LoginRequest
import com.example.vegasapp.model.authentication.RegisterRequest
import com.example.vegasapp.model.authentication.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VegasAPI {

    @POST("auth/authenticate")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<TokenResponse>

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<TokenResponse>
}