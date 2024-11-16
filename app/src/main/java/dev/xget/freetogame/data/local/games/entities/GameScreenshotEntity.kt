package dev.xget.freetogame.data.local.games.entities

import dev.xget.freetogame.domain.model.game.GameScreenshot

data class GameScreenshotEntity(
    val id: Int,
    val imageUrl: String
){
    fun toDomain(): GameScreenshot {
        return GameScreenshot(
            id = id,
            url = imageUrl
        )
    }
}