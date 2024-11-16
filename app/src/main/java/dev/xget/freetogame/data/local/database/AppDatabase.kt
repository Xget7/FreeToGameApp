package dev.xget.freetogame.data.local.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.xget.freetogame.data.local.games.FreeGameDao
import dev.xget.freetogame.data.local.games.converters.ScreenshotListConverter
import dev.xget.freetogame.data.local.games.entities.FreeGameEntity
import java.util.concurrent.Executors

@Database(entities = [FreeGameEntity::class], version = 1)
@TypeConverters(ScreenshotListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun freeGameDao(): FreeGameDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): AppDatabase {
            return instance ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "gameDatabase"
        ).setJournalMode(JournalMode.TRUNCATE)
            .setQueryCallback({ sqlQuery, bindArgs ->
                Log.d("RoomQuery", "SQL: $sqlQuery, Args: $bindArgs")
            }, Executors.newSingleThreadExecutor())
            .build()
    }

}