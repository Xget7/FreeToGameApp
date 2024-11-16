package dev.xget.freetogame.core.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import dev.xget.freetogame.FreetoGameApp
import dev.xget.freetogame.data.local.database.AppDatabase
import dev.xget.freetogame.data.local.games.FreeGameDao
import dev.xget.freetogame.data.local.games.FreeGamesLocalDataSourceI
import dev.xget.freetogame.data.local.games.FreeGamesLocalDataSourceImpl
import dev.xget.freetogame.data.remote.games.FreeGamesRemoteDataSourceI
import dev.xget.freetogame.data.remote.games.FreeGamesRemoteDataSourceImpl
import dev.xget.freetogame.data.repository.FreeGamesRepositoryImpl
import dev.xget.freetogame.domain.repository.FreeGamesRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppModule(
    private val application: FreetoGameApp,
) : AppModuleInterface {
    override val dispatcherIO: CoroutineDispatcher
        get() = Dispatchers.IO

    override val applicationContext: Context by lazy {
        application.applicationContext
    }

    private val db = AppDatabase.invoke(application)

    override val freeGamesRemoteDataSource: FreeGamesRemoteDataSourceI by lazy {
        FreeGamesRemoteDataSourceImpl(FreetoGameApp.networkModule.freeGameApiService)
    }
    override val freeGamesLocalDataSource: FreeGamesLocalDataSourceI by lazy {
        FreeGamesLocalDataSourceImpl(freeGameDao)
    }
    override val freeGamesRepository: FreeGamesRepositoryInterface
        get() = FreeGamesRepositoryImpl(freeGamesLocalDataSource, freeGamesRemoteDataSource)

    override val freeGameDao: FreeGameDao
        get() = db.freeGameDao()

}

interface AppModuleInterface {
    val applicationContext: Context
    val freeGamesRemoteDataSource: FreeGamesRemoteDataSourceI
    val freeGamesRepository: FreeGamesRepositoryInterface
    val freeGamesLocalDataSource: FreeGamesLocalDataSourceI
    val dispatcherIO: CoroutineDispatcher
    val freeGameDao: FreeGameDao
}
