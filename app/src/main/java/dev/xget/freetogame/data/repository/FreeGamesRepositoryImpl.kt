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
        if (response.isSuccess && response.getOrNull() != null) {
            freeGamesLocalDataSource.saveFreeGames(response.getOrDefault(emptyList()))
        }
        return freeGamesLocalDataSource.getFreeGames(name, category, platform)
    }


    override suspend fun getFreeGameById(id: Int): FreeGame?  {
        val localGame = freeGamesLocalDataSource.getFreeGameById(id)
        val remoteGameResponse = freeGamesRemoteDataSource.getFreeGameById(id)
        var remoteGame : FreeGame? = null
        if (remoteGameResponse.isSuccess && remoteGameResponse.getOrNull() != null) {
            remoteGame = remoteGameResponse.getOrNull()
            remoteGame?.isFavorite = localGame?.isFavorite ?: false
        }
        return remoteGame
    }

    override suspend fun deleteFavoriteGame(id: Int) {
        freeGamesLocalDataSource.deleteFavoriteGame(id)
    }

    override suspend fun saveFavoriteGame(id: Int) {
        freeGamesLocalDataSource.saveFavoriteGame(id)
    }


}