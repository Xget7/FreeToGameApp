package dev.xget.freetogame.presentation.games.details

import dev.xget.freetogame.domain.model.game.FreeGame

data class GameDetailsScreenUiState(
    val freeGame: FreeGame? = null,
    val error: String? = "",
    val isLoading: Boolean = false

)
