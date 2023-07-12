package com.example.vegasapp.model

import java.math.BigDecimal

data class UpdateBalanceRequest(
    val email: String,
    val balanceDelta: BigDecimal
)
