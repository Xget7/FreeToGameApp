package dev.xget.freetogame

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import coil.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import dev.xget.freetogame.core.di.AppComponent
import dev.xget.freetogame.core.di.NetworkModule
import dev.xget.freetogame.core.di.AppModule
import dev.xget.freetogame.core.di.DaggerAppComponent

@HiltAndroidApp
class FreetoGameApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
        appComponent.inject(this)
    }
}