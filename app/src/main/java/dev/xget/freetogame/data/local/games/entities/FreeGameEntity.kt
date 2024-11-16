package dev.xget.freetogame.data.local.games.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dev.xget.freetogame.data.local.games.converters.ScreenshotListConverter
import dev.xget.freetogame.domain.model.game.FreeGame


@Entity(tableName = "free_games")
data class FreeGameEntity(
    @PrimaryKey val id: Int,
    val isFavorite: Boolean,
    val title: String,
    val thumbnail: String,
    val status: String,
    val shortDescription: String,
    val description: String,
    val gameUrl: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val releaseDate: String,
    val freeToGameProfileUrl: String,
    @TypeConverters(ScreenshotListConverter::class) val screenshots: List<GameScreenshotEntity>
){
    fun toDomain(): FreeGame {
        return FreeGame(
            id = id,
            isFavorite = isFavorite,
            title = title,
            thumbnail = thumbnail,
            status = status,
            shortDescription = shortDescription,
            description = description,
            gameUrl = gameUrl,
            genre = genre,
            platform = platform,
            publisher = publisher,
            developer = developer,
            releaseDate = releaseDate,
            freeToGameProfileUrl = freeToGameProfileUrl,
            screenshots = screenshots.map { it.toDomain() }  // Assuming GameScreenshotEntity has a similar function
        )
    }
}