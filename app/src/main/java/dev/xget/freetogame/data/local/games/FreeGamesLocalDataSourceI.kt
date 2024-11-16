package dev.xget.freetogame.data.local.games

import dev.xget.freetogame.domain.model.game.FreeGame
import kotlinx.coroutines.flow.Flow

interface FreeGamesLocalDataSourceI {

    fun getFreeGames(
        name: String? = null,
        category: String? = null,
        platform: String? = null
    ): Flow<List<FreeGame>>

    suspend fun getFreeGameById(id: Int): FreeGame?

    suspend fun saveFreeGames(games: List<FreeGame>)

    suspend fun saveFavoriteGame(id: Int)
}