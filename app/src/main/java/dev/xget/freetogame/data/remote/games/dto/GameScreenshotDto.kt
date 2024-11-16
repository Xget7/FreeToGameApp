package dev.xget.freetogame.data.remote.games.dto

import com.google.gson.annotations.SerializedName
import dev.xget.freetogame.domain.model.game.GameScreenshot

data class GameScreenshotDto(
    val id: Int,
    @SerializedName("image")
    val imageUrl: String
){
    fun toGameScreenshot(): GameScreenshot {
        return GameScreenshot(
            id = id,
            url = imageUrl
        )
    }
}
