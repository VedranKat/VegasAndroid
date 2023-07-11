package com.example.vegasapp.model

data class TicketGameResponse(
    val id: Long,
    val chosenWinner: String,
    val gameResponse: GameResponse
)
