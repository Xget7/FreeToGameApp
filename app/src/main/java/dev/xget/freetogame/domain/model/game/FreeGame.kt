package dev.xget.freetogame.domain.model.game

import com.google.gson.annotations.SerializedName
import dev.xget.freetogame.data.local.games.entities.FreeGameEntity
import dev.xget.freetogame.data.remote.games.dto.GameScreenshotDto

data class FreeGame(
    val id: Int,
    val title: String = "",
    var isFavorite: Boolean = false,
    val thumbnail: String = "",
    val status: String = "",
    val shortDescription: String = "",
    val description: String? = "",
    val gameUrl: String? = "",
    val genre: String? = "",
    val platform: String? = "",
    val publisher: String? = "",
    val developer: String? = "",
    val releaseDate: String? = "",
    val freeToGameProfileUrl: String = "",
    val screenshots: List<GameScreenshot> = emptyList()
){
    fun toEntity(): FreeGameEntity {
        return FreeGameEntity(
            id = id,
            isFavorite = isFavorite,
            title = title,
            thumbnail = thumbnail,
            status = status,
            shortDescription = shortDescription,
            description = description ?: "",
            gameUrl = gameUrl ?: "",
            genre = genre ?: "",
            platform = platform ?: "",
            publisher = publisher ?: "",
            developer = developer ?: "",
            releaseDate = releaseDate ?: "",
            freeToGameProfileUrl = freeToGameProfileUrl,
            screenshots = screenshots.map { it.toEntity() }  // Assuming GameScreenshot has a similar function
        )
    }
}
