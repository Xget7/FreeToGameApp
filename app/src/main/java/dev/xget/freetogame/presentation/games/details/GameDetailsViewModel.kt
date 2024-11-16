package dev.xget.freetogame.presentation.games.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.xget.freetogame.domain.repository.FreeGamesRepositoryInterface
import dev.xget.freetogame.presentation.games.home.HomeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameDetailsViewModel (
    private val freeGameId: String,
    private val gamesRepository: FreeGamesRepositoryInterface
) : ViewModel() {

    private val _state = MutableStateFlow(GameDetailsScreenUiState())
    val state = _state.asStateFlow()


    init {
        if (freeGameId.isEmpty()) {
            _state.value = _state.value.copy(error = "No se encontro ningun juego")
        }else{
            Log.d("GameDetailsViewModel", "init: $freeGameId")
            getFreeGameById(freeGameId.toInt())
        }
    }

    private fun getFreeGameById(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val freeGame = gamesRepository.getFreeGameById(id)
            Log.d("GameDetailsViewModel", "getFreeGameById: $freeGame")
            _state.value = _state.value.copy(freeGame = freeGame, isLoading = false)
        }
    }

    fun resetError(){
        _state.value = _state.value.copy(error = null)
    }

    fun saveFavoriteGame() {
        viewModelScope.launch {
            gamesRepository.saveFavoriteGame(freeGameId.toInt())
        }
    }

}