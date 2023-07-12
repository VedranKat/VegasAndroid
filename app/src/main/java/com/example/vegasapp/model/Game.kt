package com.example.vegasapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "game")
data class Game(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val gameApiId: String?,
    val chosenWinner: String,
    val homeTeam: String?,
    val awayTeam: String?,
    val homeTeamOdd: Double?,
    val awayTeamOdd: Double?,
): Serializable
