package dev.xget.freetogame.data.remote.games.dto

import com.google.gson.annotations.SerializedName
import dev.xget.freetogame.domain.model.game.FreeGame

data class FreeGameDto(
    val id: Int,
    val title: String,
    val thumbnail: String? = "",
    val status: String? = "",
    @SerializedName("short_description")
    val shortDescription: String? = "",
    val description: String? = "",
    @SerializedName("game_url")
    val gameUrl: String? = "",
    val genre: String? = "",
    val platform: String? = "",
    val publisher: String? = "",
    val developer: String? = "",
    @SerializedName("release_date")
    val releaseDate: String? = "",
    @SerializedName("freetogame_profile_url")
    val freeToGameProfileUrl: String? = "",
    val screenshots: List<GameScreenshotDto>? = emptyList()
){
    fun toFreeGame(): FreeGame {
        return FreeGame(
            id = id,
            isFavorite = false,
            title = title,
            thumbnail = thumbnail ?: "",
            status = status ?: "",
            shortDescription = shortDescription ?: "",
            description = description ?: "",
            gameUrl = gameUrl ?: "",
            genre = genre ?: "",
            platform = platform ?: "",
            publisher = publisher ?: "",
            developer = developer ?: "",
            releaseDate = releaseDate ?: "",
            freeToGameProfileUrl = freeToGameProfileUrl ?: "",
            screenshots = screenshots?.map { it.toGameScreenshot() } ?: emptyList()
        )
    }
}
