package dev.xget.freetogame.data.repository.games

import dev.xget.freetogame.domain.model.game.FreeGame
import dev.xget.freetogame.domain.repository.FreeGamesRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGamesRepository(
    var games : List<FreeGame>
) : FreeGamesRepositoryInterface{



    override suspend fun getFreeGames(
        byName: String,
        sortBy: String,
        category: String,
        platform: String
    ): Flow<List<FreeGame>> = flow {
        emit(games)
    }

    override suspend fun getFreeGameById(id: Int): FreeGame? {
        return games.find { it.id == id }
    }

    override suspend fun deleteFavoriteGame(id: Int) {
        games.find { it.id == id }?.let {
            it.isFavorite = false
        }
    }

    override suspend fun saveFavoriteGame(id: Int) {
        games.find { it.id == id }?.let {
            it.isFavorite = true
        }
    }
}