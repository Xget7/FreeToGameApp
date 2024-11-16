package dev.xget.freetogame.data.local.games

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.xget.freetogame.data.local.games.entities.FreeGameEntity
import dev.xget.freetogame.domain.model.game.FreeGame
import kotlinx.coroutines.flow.Flow

@Dao
interface FreeGameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFreeGames(games: List<FreeGameEntity>)

    @Query(
        "SELECT * FROM free_games WHERE (:name IS NULL OR title LIKE '%' || :name || '%') AND (:category IS NULL OR genre LIKE '%' || :category || '%') AND (:platform IS NULL OR platform LIKE '%' || :platform || '%')"
    )
    fun getFilteredFreeGames(
        name: String?,
        category: String?,
        platform: String?
    ): Flow<List<FreeGameEntity>>

    @Query("SELECT * FROM free_games WHERE isFavorite = 1")
    suspend fun getFavoriteGames(): List<FreeGameEntity>

    @Query("SELECT * FROM free_games WHERE id = :id")
    suspend fun getFreeGameById(id: Int): FreeGameEntity

    @Query("DELETE FROM free_games")
    suspend fun deleteAllFreeGames()


    @Query("UPDATE free_games SET isFavorite = 1 WHERE id = :id")
    suspend fun saveFavoriteGame(id: Int)
}