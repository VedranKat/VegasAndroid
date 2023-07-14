package com.example.vegasapp.model

import java.math.BigDecimal

data class PlayTicketRequest(
    val email: String,
    val price : BigDecimal,
    val winAmount: BigDecimal,
    val games : List<PlayGameRequest>
)