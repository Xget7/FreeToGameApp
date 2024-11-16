package dev.xget.freetogame.data.local.games

import android.util.Log
import dev.xget.freetogame.domain.model.game.FreeGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FreeGamesLocalDataSourceImpl(
    private val freeGameDao: FreeGameDao
) : FreeGamesLocalDataSourceI {

    override fun getFreeGames(name: String?, category: String?, platform: String?): Flow<List<FreeGame>> {
        return freeGameDao.getFilteredFreeGames(name, category, platform)
            .map { game -> game.map { it.toDomain() } }
    }

    override suspend fun getFreeGameById(id: Int): FreeGame? {
        val response = freeGameDao.getFreeGameById(id)
        if (response != null) {
            return response.toDomain()
        }
        return null
    }

    override suspend fun saveFreeGames(games: List<FreeGame>) {
        // Get the existing favorite games
        val favoriteGames = freeGameDao.getFavoriteGames().associateBy { it.id }

        // Update new games list with favorite status
        val gamesToInsert = games.map { game ->
            if (favoriteGames.containsKey(game.id)) {
                game.copy(isFavorite = true)  // Preserve favorite status
            } else {
                game
            }
        }.map { it.toEntity() }

        // Insert the updated list
        freeGameDao.insertFreeGames(gamesToInsert)
    }

    override suspend fun saveFavoriteGame(id: Int) {
        freeGameDao.saveFavoriteGame(id)
    }
}