package com.example.vegasapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vegasapp.model.Game

@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(game: Game): Long

    @Query("SELECT * FROM game")
    suspend fun getAllGames(): List<Game> //livedata problem

    @Delete
    suspend fun deleteGame(game: Game)

    @Delete
    suspend fun deleteAllGames(games: List<Game>)

    @Query("SELECT COUNT(*) FROM game WHERE gameApiId = :gameApiId")
    suspend fun getGameCountByApiId(gameApiId: String): Int
}