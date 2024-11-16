package dev.xget.freetogame.data.remote.games

import android.util.Log
import dev.xget.freetogame.data.remote.api.ApiResponse
import dev.xget.freetogame.data.remote.api.FreeGameApiService
import dev.xget.freetogame.data.remote.games.dto.FreeGameDto
import dev.xget.freetogame.domain.model.game.FreeGame

class FreeGamesRemoteDataSourceImpl(
    private val freeGameApiService: FreeGameApiService
) : FreeGamesRemoteDataSourceI {

    override suspend fun getFreeGames(
        sortBy: String?,
        category: String?,
        platform: String?
    ): Result<List<FreeGame>> {
        val apiResponse = freeGameApiService.getAllGames(sortBy, category, platform)
        try {
            if (apiResponse.isSuccessful) {
                val freeGameDtos = apiResponse.body().orEmpty().map { it.toFreeGame() }
                Log.d("API REMOTE DATA SOURCE", freeGameDtos.toString())
                return Result.success(freeGameDtos)
            } else {
                return Result.failure(Throwable(apiResponse.errorBody()!!.string()))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getFreeGameById(id: Int): Result<FreeGame?> {
        val apiResponse = freeGameApiService.getGameById(id.toString())
        try {
            if (apiResponse.isSuccessful) {
                val freeGameDto = apiResponse.body()
                return Result.success(freeGameDto?.toFreeGame())
            } else {
                return Result.failure(Throwable(apiResponse.errorBody()!!.string()))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}