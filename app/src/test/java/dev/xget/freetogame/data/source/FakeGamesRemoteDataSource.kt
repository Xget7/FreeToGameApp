package dev.xget.freetogame.data.source

import dev.xget.freetogame.data.remote.games.FreeGamesRemoteDataSourceI
import dev.xget.freetogame.domain.model.game.FreeGame

class FakeGamesRemoteDataSource : FreeGamesRemoteDataSourceI {

    private val freeGamesList = listOf(
        FreeGame(id = 1, title = "Game 1", description = "Description for Game 1", platform = "PC"),
        FreeGame(id = 2, title = "Game 2", description = "Description for Game 2", platform = "PC"),
        FreeGame(id = 3, title = "Game 3", description = "Description for Game 3", platform = "Web Browser"),
    )

    override suspend fun getFreeGames(
        sortBy: String?,
        category: String?,
        platform: String?
    ): Result<List<FreeGame>> {
        // Returning a predefined list of free games
        return Result.success(freeGamesList)
    }

    override suspend fun getFreeGameById(id: Int): Result<FreeGame?> {
        // Finding a game by ID from the predefined list
        val game = freeGamesList.find { it.id == id }
        return if (game != null) {
            Result.success(game)
        } else {
            Result.failure(Exception("Game not found"))
        }
    }
}
