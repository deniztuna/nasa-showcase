package com.appcent.nasashowcase.di

import android.content.Context
import com.appcent.nasashowcase.data.source.remote.NasaService
import com.appcent.nasashowcase.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideNasaService(okHttpClient: OkHttpClient): NasaService {
        return Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NasaService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10 MB Cache
        val myCache = Cache(appContext.cacheDir, cacheSize)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .cache(myCache)
            .addNetworkInterceptor { chain ->
                val originalResponse: Response = chain.proceed(chain.request())
                val cacheControl = originalResponse.header("Cache-Control")
                if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains(
                        "no-cache"
                    ) ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")
                ) {
                    originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 5000)
                        .build()
                } else {
                    originalResponse
                }
            }
            .addInterceptor { chain ->
                var request: Request = chain.request()
                if (!Util.isOnline(appContext)) {
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached")
                        .build()
                }
                chain.proceed(request)
            }
            .addInterceptor(interceptor)
            .build()
    }
}