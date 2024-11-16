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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        appContext: Context
    ): OkHttpClient {
        val cacheSize = (5 * 1024 * 1024).toLong() // 5 MB
        val cache = Cache(appContext.cacheDir, cacheSize)

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                var request = chain.request()

                request = if (hasNetwork(appContext) == true) {
                    // Get Cache stored 5 seconds ago
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                } else {
                    // Get Cache stored 7 days ago if no internet connection
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

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder().setLenient().create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(HttpRoutes.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    fun provideFreeGameApiService(retrofit: Retrofit): FreeGameApiService {
        return retrofit.create(FreeGameApiService::class.java)
    }
}
