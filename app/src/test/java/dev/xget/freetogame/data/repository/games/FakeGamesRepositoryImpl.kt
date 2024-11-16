package dev.xget.freetogame.data.repository.games

import dev.xget.freetogame.data.local.games.FreeGamesLocalDataSourceI
import dev.xget.freetogame.data.remote.games.FreeGamesRemoteDataSourceI
import dev.xget.freetogame.data.repository.FreeGamesRepositoryImpl
import dev.xget.freetogame.data.source.FakeGamesLocalDataSource
import dev.xget.freetogame.data.source.FakeGamesRemoteDataSource
import dev.xget.freetogame.domain.repository.FreeGamesRepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

//Unit tests for FakeGamesRepositoryImpl
class FakeGamesRepositoryImplTest {

    private lateinit var fakeGamesRepository: FreeGamesRepositoryInterface
    private lateinit var fakeGamesLocalDataSource: FreeGamesLocalDataSourceI
    private lateinit var fakeGamesRemoteDataSource: FreeGamesRemoteDataSourceI

    private val testDispatcher = StandardTestDispatcher(scheduler = TestCoroutineScheduler())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        fakeGamesLocalDataSource = FakeGamesLocalDataSource() // Initialize with some fake data
        fakeGamesRemoteDataSource = FakeGamesRemoteDataSource() // Initialize with some fake data
        Dispatchers.setMain(testDispatcher)
        fakeGamesRepository =
            FreeGamesRepositoryImpl(fakeGamesLocalDataSource, fakeGamesRemoteDataSource)
    }

    @Test
    fun `getFreeGames returns a list of free games from local data source`() = runTest {
        val result = fakeGamesRepository.getFreeGames("game", "", "", "").first()
        assertEquals(3, result) // Assuming FakeGamesLocalDataSource has 3 items
    }

    @Test
    fun `getFreeGameById returns the correct game by ID`() = runTest {
        val game = fakeGamesRepository.getFreeGameById(1)
        assertNotNull(game)
        assertEquals(1, game?.id)
    }

    @Test
    fun `getFreeGameById returns null for non-existent game`() = runTest {
        val game = fakeGamesRepository.getFreeGameById(999)
        assertNull(game)
    }

    @Test
    fun `saveFavoriteGame marks the game as favorite`() = runTest {
        fakeGamesRepository.saveFavoriteGame(1)
        val game = fakeGamesRepository.getFreeGameById(1)
        assertNotNull(game)
        assertTrue(game?.isFavorite == true)
    }

    @Test
    fun `deleteFavoriteGame removes the game from favorites`() = runTest {
        fakeGamesRepository.saveFavoriteGame(1)
        fakeGamesRepository.deleteFavoriteGame(1)
        val game = fakeGamesRepository.getFreeGameById(1)
        assertNotNull(game)
        assertTrue(game?.isFavorite == false)
    }
}
