package dev.xget.freetogame.data.remote.games

import dev.xget.freetogame.data.remote.api.ApiResponse
import dev.xget.freetogame.data.remote.games.dto.FreeGameDto
import dev.xget.freetogame.domain.model.game.FreeGame
import retrofit2.Response

interface FreeGamesRemoteDataSourceI {

    suspend fun getFreeGames(
        sortBy: String? = null,
        category: String? = null,
        platform: String? = null
    ): Result<List<FreeGame>>

    suspend fun getFreeGameById(id: Int): Result<FreeGame?>

}