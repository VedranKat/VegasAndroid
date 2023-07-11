package com.example.vegasapp.model

import java.util.Date

data class GameResponse(
    val gameApiId: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamOdd: Double,
    val awayTeamOdd: Double,
    val finished: Boolean,
    val winner: String?,
    val homeTeamScore: Double?,
    val awayTeamScore: Double?,
    val commenceTime: Date
)
