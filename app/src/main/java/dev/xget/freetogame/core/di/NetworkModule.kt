package dev.xget.freetogame.core.di

import android.content.Context
import com.google.gson.GsonBuilder
import dev.xget.freetogame.core.utils.NetworkUtils.hasNetwork
import dev.xget.freetogame.data.remote.api.FreeGameApiService
import dev.xget.freetogame.core.utils.HttpRoutes

import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface NetworkModuleI {
    val httpLoggingInterceptor: HttpLoggingInterceptor
    val okHttpClient: OkHttpClient
    val gsonConverterFactory: GsonConverterFactory
    val retrofit: Retrofit
    val freeGameApiService: FreeGameApiService
}

class NetworkModule(
    private val appContext: Context
) : NetworkModuleI {
    override val httpLoggingInterceptor: HttpLoggingInterceptor by lazy {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    override val okHttpClient: OkHttpClient by lazy {
        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        val cache = Cache(appContext.cacheDir, cacheSize)
        OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()

                request = if (hasNetwork(appContext) == true) {
                    //Get Cache stored 5 seconds ago
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                } else {
                    //Get Cache stored 7 days ago if not connected to the internet   if the cache is older than 7 days, then discard it,
                    //and indicate an error in fetching the response.
                    val maxStale = 60 * 60 * 24 * 7 // 1-week stale
                    request.newBuilder().header(
                        "Cache-Control",
                        "public, only-if-cached, max-stale=$maxStale"
                    ).build()
                }

                chain.proceed(request)
            }
            .build()
    }
    override val gsonConverterFactory: GsonConverterFactory by lazy {
        val gson = GsonBuilder().setLenient().create()

        GsonConverterFactory.create(gson)
    }
    override val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(HttpRoutes.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
    override val freeGameApiService: FreeGameApiService by lazy {
        retrofit.create(FreeGameApiService::class.java)
    }


}
