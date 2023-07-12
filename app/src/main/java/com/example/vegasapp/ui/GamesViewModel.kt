package com.example.vegasapp.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vegasapp.db.GameDatabase
import com.example.vegasapp.model.Game
import com.example.vegasapp.model.GameResponse
import com.example.vegasapp.repository.GameRepository
import com.example.vegasapp.repository.GameRepositoryDB
import com.example.vegasapp.util.Resource
import com.example.vegasapp.util.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    val gameRepository: GameRepository,
    @ApplicationContext val context: Context // bandaid
) : ViewModel() {

    val games: MutableLiveData<Resource<List<GameResponse>>> = MutableLiveData()

    //No DI
    val gameDb = GameDatabase.invoke(context)
    val gameRepositoryDB = GameRepositoryDB(gameDb)

    init {
        val token = TokenManager.getToken(context)
        getGames(token!!)
    }

    fun getGames(token: String) = viewModelScope.launch {
        games.postValue(Resource.Loading())
        val response = gameRepository.getOdds(token)
        games.postValue(handleGamesResponse(response))
    }

    private fun handleGamesResponse(response: Response<List<GameResponse>>): Resource<List<GameResponse>> {
        if (response.isSuccessful) {
            val gameResponse = response.body()
            gameResponse?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveGameToDatabase(gameResponse: GameResponse, chosenWinner: String) = viewModelScope.launch {

        val game = Game(
            null,
            gameResponse.gameApiId,
            chosenWinner,
            gameResponse.homeTeam,
            gameResponse.awayTeam,
            gameResponse.homeTeamOdd,
            gameResponse.awayTeamOdd
        )

        val isGameExists = gameRepositoryDB.isGameExistsByApiId(gameResponse.gameApiId)

        if(isGameExists){
            Toast.makeText(context, "Bet already placed", Toast.LENGTH_LONG).show()
        }else{
            gameRepositoryDB.insertGame(game)
            Toast.makeText(context, "${chosenWinner} added", Toast.LENGTH_SHORT).show()
        }


    }

}