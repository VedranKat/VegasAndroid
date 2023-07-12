package com.example.vegasapp.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vegasapp.db.GameDatabase
import com.example.vegasapp.model.Game
import com.example.vegasapp.model.GameResponse
import com.example.vegasapp.repository.GameRepositoryDB
import com.example.vegasapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ActiveTicketGamesViewModel @Inject constructor(
    @ApplicationContext val context: Context // bandaid
) : ViewModel(){

    val games: MutableLiveData<List<Game>> = MutableLiveData()

    //No DI
    val gameDb = GameDatabase.invoke(context)
    val gameRepositoryDB = GameRepositoryDB(gameDb)

    init {
        getAllGames()
    }

    private fun getAllGames() = viewModelScope.launch {
        val response = gameRepositoryDB.getAllGames()
        games.postValue(response)
    }

    fun deleteGame(game: Game) = viewModelScope.launch {
        gameRepositoryDB.deleteGame(game)
        val response = gameRepositoryDB.getAllGames()
        games.postValue(response)
    }

    fun refreshGames() = viewModelScope.launch {
        val response = gameRepositoryDB.getAllGames()
        games.postValue(response)
    }


}