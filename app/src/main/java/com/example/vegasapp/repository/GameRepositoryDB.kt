package com.example.vegasapp.repository
import com.example.vegasapp.db.GameDatabase
import com.example.vegasapp.model.Game

class GameRepositoryDB(val gameDb: GameDatabase) {

    suspend fun insertGame(game: Game) = gameDb.getGameDao().upsert(game)

    suspend fun getAllGames() = gameDb.getGameDao().getAllGames()

    suspend fun deleteGame(game: Game) = gameDb.getGameDao().deleteGame(game)

    suspend fun deleteAllGames(games: List<Game>) = gameDb.getGameDao().deleteAllGames(games)

    suspend fun isGameExistsByApiId(gameApiId: String): Boolean {
        val count = gameDb.getGameDao().getGameCountByApiId(gameApiId)
        return count > 0
    }
}