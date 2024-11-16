package dev.xget.freetogame.core.di

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.xget.freetogame.FreetoGameApp
import dev.xget.freetogame.data.local.database.AppDatabase
import dev.xget.freetogame.data.local.games.FreeGameDao
import dev.xget.freetogame.data.local.games.FreeGamesLocalDataSourceI
import dev.xget.freetogame.data.local.games.FreeGamesLocalDataSourceImpl
import dev.xget.freetogame.data.remote.api.FreeGameApiService
import dev.xget.freetogame.data.remote.games.FreeGamesRemoteDataSourceI
import dev.xget.freetogame.data.remote.games.FreeGamesRemoteDataSourceImpl
import dev.xget.freetogame.data.repository.FreeGamesRepositoryImpl
import dev.xget.freetogame.domain.repository.FreeGamesRepositoryInterface
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase = AppDatabase.invoke(context)

    @Provides
    @Singleton
    fun provideFreeGameDao(appDatabase: AppDatabase): FreeGameDao = appDatabase.freeGameDao()

    @Provides
    @Singleton
    fun provideFreeGamesRemoteDataSource(freeGameApiService: FreeGameApiService): FreeGamesRemoteDataSourceI {
        return FreeGamesRemoteDataSourceImpl(freeGameApiService)
    }

    @Provides
    @Singleton
    fun provideFreeGamesLocalDataSource(freeGameDao: FreeGameDao): FreeGamesLocalDataSourceI {
        return FreeGamesLocalDataSourceImpl(freeGameDao)
    }

    @Provides
    @Singleton
    fun provideFreeGamesRepository(
        freeGamesLocalDataSource: FreeGamesLocalDataSourceI,
        freeGamesRemoteDataSource: FreeGamesRemoteDataSourceI
    ): FreeGamesRepositoryInterface {
        return FreeGamesRepositoryImpl(freeGamesLocalDataSource, freeGamesRemoteDataSource)
    }
}


@Component(modules = [AppModule::class , NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(application: FreetoGameApp)
}
