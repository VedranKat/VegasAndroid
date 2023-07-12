package com.example.vegasapp.model

import java.math.BigDecimal

data class UserResponse(
    val id: Long,
    val email: String,
    val balance: BigDecimal
)
