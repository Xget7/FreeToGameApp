package dev.xget.freetogame.data.local.games.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.xget.freetogame.data.local.games.entities.GameScreenshotEntity


class ScreenshotListConverter {

    @TypeConverter
    fun fromScreenshotList(screenshots: List<GameScreenshotEntity>): String {
        val gson = Gson()
        return gson.toJson(screenshots)
    }

    @TypeConverter
    fun toScreenshotList(screenshotString: String): List<GameScreenshotEntity> {
        val listType = object : TypeToken<List<GameScreenshotEntity>>() {}.type
        return Gson().fromJson(screenshotString, listType)
    }
}