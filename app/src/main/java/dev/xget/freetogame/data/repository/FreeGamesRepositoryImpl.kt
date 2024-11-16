package dev.xget.freetogame.data.repository

import android.util.Log
import dev.xget.freetogame.data.local.games.FreeGamesLocalDataSourceI
import dev.xget.freetogame.data.remote.games.FreeGamesRemoteDataSourceI
import dev.xget.freetogame.domain.model.game.FreeGame
import dev.xget.freetogame.domain.repository.FreeGamesRepositoryInterface
import kotlinx.coroutines.flow.Flow

class FreeGamesRepositoryImpl(
    private val freeGamesLocalDataSource: FreeGamesLocalDataSourceI,
    private val freeGamesRemoteDataSource: FreeGamesRemoteDataSourceI
) : FreeGamesRepositoryInterface {

    override suspend fun getFreeGames(
        name : String,
        sortBy: String,
        category: String,
        platform: String
    ): Flow<List<FreeGame>> {
        val response = freeGamesRemoteDataSource.getFreeGames()
        Log.d("APiGet", response.toString())
        if (response.isSuccess && response.getOrNull() != null) {
            freeGamesLocalDataSource.saveFreeGames(response.getOrDefault(emptyList()))
            Log.d("APiGet", "Saved")
        }
        return freeGamesLocalDataSource.getFreeGames(name, category, platform)
    }


    override suspend fun getFreeGameById(id: Int): FreeGame?  {
        val localGame = freeGamesLocalDataSource.getFreeGameById(id)
        val remoteGameResponse = freeGamesRemoteDataSource.getFreeGameById(id)
        Log.d("API REMOTE DATA SOURCE Repo", "getFreeGameById: ${remoteGameResponse.getOrThrow()}")
        var remoteGame : FreeGame? = null
        if (remoteGameResponse.isSuccess && remoteGameResponse.getOrNull() != null) {
            remoteGame = remoteGameResponse.getOrNull()
            remoteGame?.isFavorite = localGame?.isFavorite ?: false
        }
        return remoteGame
    }

    override suspend fun saveFavoriteGame(id: Int) {
        freeGamesLocalDataSource.saveFavoriteGame(id)
    }


}