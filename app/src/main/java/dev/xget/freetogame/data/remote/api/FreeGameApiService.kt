package dev.xget.freetogame.data.remote.api

import dev.xget.freetogame.core.utils.HttpRoutes.GET_GAMES
import dev.xget.freetogame.core.utils.HttpRoutes.GET_UNIQUE_GAME
import dev.xget.freetogame.data.remote.games.dto.FreeGameDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FreeGameApiService {

    @GET(GET_GAMES)
    suspend fun getAllGames(
        @Query("sort-by") sortBy: String?,
        @Query("category") category: String?,
        @Query("platform") platform: String?
    ): Response<List<FreeGameDto>>

    @GET(GET_UNIQUE_GAME)
    suspend fun getGameById(
        @Query("id") id: String
    ): Response<FreeGameDto>
}