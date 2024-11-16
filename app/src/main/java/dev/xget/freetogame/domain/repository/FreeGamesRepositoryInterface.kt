package dev.xget.freetogame.domain.repository

import dev.xget.freetogame.domain.model.game.FreeGame
import kotlinx.coroutines.flow.Flow

interface FreeGamesRepositoryInterface {

    suspend fun getFreeGames(
        byName : String,
        sortBy: String,
        category: String,
        platform: String
    ): Flow<List<FreeGame>>

    suspend fun getFreeGameById(id: Int): FreeGame?



    suspend fun saveFavoriteGame(id: Int)
}