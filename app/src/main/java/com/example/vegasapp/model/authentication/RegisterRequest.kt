package com.example.vegasapp.model.authentication

data class RegisterRequest(
    val email: String,
    val password: String,
    val confirmPassword: String
)
