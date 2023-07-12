package com.example.vegasapp.api

import com.example.vegasapp.model.GameResponse
import com.example.vegasapp.model.TicketGameResponse
import com.example.vegasapp.model.TicketResponse
import com.example.vegasapp.model.UpdateBalanceRequest
import com.example.vegasapp.model.UserResponse
import com.example.vegasapp.model.authentication.LoginRequest
import com.example.vegasapp.model.authentication.RegisterRequest
import com.example.vegasapp.model.authentication.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface VegasAPI {


    //Login and Register
    @POST("auth/authenticate")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<TokenResponse>

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<TokenResponse>

    //Games
    @GET("game/get-odds")
    suspend fun getOdds(
        @Header("Authorization") token: String
    ): Response<List<GameResponse>>

    //Tickets
    @GET("ticket/get-all/{email}")
    suspend fun getTickets(
        @Header("Authorization") token: String,
        @Path("email") email: String
    ): Response<List<TicketResponse>>

    @POST("ticket/create")
    suspend fun createTicket(
        @Header("Authorization") token: String,
        @Body ticketResponse: TicketResponse //TODO: Change to TicketRequest
    ): Response<TicketResponse>

    //Games in ticket
    @GET("ticket-game/get-by-ticket-id/{id}")
    suspend fun getTicketGames(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<List<TicketGameResponse>>

    //User
    @GET("user/get-user/{email}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("email") email: String
    ): Response<UserResponse>

    @PUT("user/update-balance")
    suspend fun updateBalance(
        @Header("Authorization") token: String,
        @Body updateBalanceRequest: UpdateBalanceRequest
    ): Response<UserResponse>

}