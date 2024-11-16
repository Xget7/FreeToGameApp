package dev.xget.freetogame.domain.model.game

import dev.xget.freetogame.data.local.games.entities.GameScreenshotEntity

data class GameScreenshot(
    val url: String? = "",
    val id: Int = 0
){
    fun toEntity(): GameScreenshotEntity {
        return GameScreenshotEntity(
            id = id,
            imageUrl = url ?: ""
        )
    }
}