package dev.xget.freetogame.presentation.games.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.xget.freetogame.domain.repository.FreeGamesRepositoryInterface
import dev.xget.freetogame.presentation.games.home.HomeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val gamesRepository: FreeGamesRepositoryInterface
) : ViewModel() {

    private val _state = MutableStateFlow(GameDetailsScreenUiState())
    val state = _state.asStateFlow()

    private val freeGameId = savedStateHandle["freeGameId"] ?: ""

    init {
        Log.d("GameDetailsViewModel", "init: $freeGameId")
        if (freeGameId.isEmpty()) {
            _state.value = _state.value.copy(error = "No se encontro ningun juego")
        }else{
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

    fun saveFavoriteGame() {
        viewModelScope.launch {
            if (state.value.freeGame != null && state.value.freeGame?.isFavorite == false) {
                _state.value = _state.value.copy(freeGame = state.value.freeGame?.copy(isFavorite = true))
                gamesRepository.saveFavoriteGame(freeGameId.toInt())
            }else{
                _state.value = _state.value.copy(freeGame = state.value.freeGame?.copy(isFavorite = false))
                gamesRepository.deleteFavoriteGame(freeGameId.toInt())
            }
        }
    }

}