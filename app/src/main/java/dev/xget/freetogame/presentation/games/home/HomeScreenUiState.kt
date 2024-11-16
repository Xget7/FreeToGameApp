package dev.xget.freetogame.presentation.games.home

import androidx.room.Query
import dev.xget.freetogame.data.remote.utils.GameApiConstants
import dev.xget.freetogame.domain.model.game.FreeGame

data class HomeScreenUiState(
    val isLoading : Boolean = true,
    val games : List<FreeGame> = emptyList(),

    //filter states
    val nameQuery: String = "",
    val filterPlatform: String = "",
    val filterCategory: String = "",
    val filterOrderBy: String = "",

    val platformList: Map<String, String> = GameApiConstants.GamePlatforms,
    val categoryList: List<String> = GameApiConstants.GameTags,
    val orderByList: Map<String, String> = GameApiConstants.GameSortOptions,


    val filterCounter: Int = 0,
    val error: String? = ""
)