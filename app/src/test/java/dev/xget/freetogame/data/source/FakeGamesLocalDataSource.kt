package dev.xget.freetogame.data.source

import dev.xget.freetogame.data.local.games.FreeGamesLocalDataSourceI
import dev.xget.freetogame.domain.model.game.FreeGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGamesLocalDataSource : FreeGamesLocalDataSourceI {

    private val freeGamesList = mutableListOf(
        FreeGame(
            id = 1,
            title = "Game 1",
            shortDescription = "Description for Game 1",
            platform = "PC"
        ),
        FreeGame(
            id = 2,
            title = "Game 2",
            shortDescription = "Description for Game 2",
            platform = "PC"
        ),
        FreeGame(
            id = 3,
            title = "Game 3",
            shortDescription = "Description for Game 3",
            platform = "Mobile"
        )
    )

    private val favoriteGamesIds = mutableSetOf<Int>()

    override fun getFreeGames(
        name: String?,
        category: String?,
        platform: String?
    ): Flow<List<FreeGame>> = flow {
        // Returning a flow of the predefined list of games (filtering can be applied based on params if needed)
        val freeGames = freeGamesList.filter { game ->
            (name.isNullOrBlank() || game.title.contains(name, ignoreCase = true)) &&
                    (platform.isNullOrBlank() || game.platform == platform)
        }

        emit(freeGames)

    }

    override suspend fun getFreeGameById(id: Int): FreeGame? {
        // Searching for a game by its ID
        return freeGamesList.find { it.id == id }
    }

    override suspend fun saveFreeGames(games: List<FreeGame>) {
        // Saving games to the local data source
        freeGamesList.addAll(games)
    }

    override suspend fun saveFavoriteGame(id: Int) {
        // Marking a game as a favorite
        favoriteGamesIds.add(id)
    }

    override suspend fun deleteFavoriteGame(id: Int) {
        // Removing a game from favorites
        favoriteGamesIds.remove(id)
    }

    // Optional helper method to simulate getting favorite games
    fun getFavoriteGames(): List<FreeGame> {
        return freeGamesList.filter { favoriteGamesIds.contains(it.id) }
    }
}
