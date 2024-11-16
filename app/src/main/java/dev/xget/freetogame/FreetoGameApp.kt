package dev.xget.freetogame

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import coil.util.DebugLogger
import dev.xget.freetogame.core.di.NetworkModule
import dev.xget.freetogame.core.di.AppModule

class FreetoGameApp : Application() {

    override fun onCreate() {
        super.onCreate()
        networkModule = NetworkModule(this)
        appModule = AppModule(this)
    }

    //companion object to access the network module from all over the app
    companion object {
        lateinit var networkModule: NetworkModule
        lateinit var appModule: AppModule
    }


}