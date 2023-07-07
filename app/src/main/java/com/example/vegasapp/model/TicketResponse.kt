package com.example.vegasapp.model

import java.math.BigDecimal
import java.util.Date

data class TicketResponse(
    val id: Long,
    val price: BigDecimal,
    val winAmount: BigDecimal,
    val won: Boolean?,
    val finished: Boolean,
    val dateCreated: Date
)
