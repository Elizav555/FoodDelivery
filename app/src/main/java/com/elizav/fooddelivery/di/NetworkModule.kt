package com.elizav.fooddelivery.di

import android.content.Context
import com.elizav.fooddelivery.BuildConfig
import com.elizav.fooddelivery.data.api.MealsApi
import com.elizav.fooddelivery.di.qualifiers.ApiInterceptor
import com.elizav.fooddelivery.di.qualifiers.CacheInterceptor
import com.elizav.fooddelivery.di.qualifiers.HostInterceptor
import com.elizav.fooddelivery.di.qualifiers.InternetInterceptor
import com.elizav.fooddelivery.ui.utils.InternetConnectionControl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun loggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    @Provides
    @Singleton
    @ApiInterceptor
    fun provideApiKeyInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val newRequest = original.newBuilder()
            .addHeader(API_KEY_HEADER, API_KEY)
            .build()
        chain.proceed(
            newRequest
        )
    }

    @Provides
    @Singleton
    @HostInterceptor
    fun provideHostInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val newRequest = original.newBuilder()
            .addHeader(HOST_HEADER, HOST)
            .build()
        chain.proceed(
            newRequest
        )
    }

    @Provides
    @Singleton
    @CacheInterceptor
    fun provideCacheInterceptor() =
        Interceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            val maxAge: Int = originalResponse.cacheControl.maxAgeSeconds
            if (maxAge <= 0) {
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Expires")
                    .removeHeader("Cache-Control")
                    .header(
                        "Cache-Control",
                        String.format(
                            Locale.ENGLISH,
                            "max-age=%d, only-if-cached, max-stale=%d",
                            CACHE_DURATION_SEC,
                            0
                        )
                    )
                    .build()
            } else {
                originalResponse
            }
        }

    @InternetInterceptor
    @Provides
    fun provideInternetInterceptor(
        internetConnectionControl: InternetConnectionControl
    ) = Interceptor { chain ->
        var request = chain.request()
        if (request.cacheControl.noCache) {
            return@Interceptor chain.proceed(request)
        }
        request =
            if (internetConnectionControl.isNetworkConnected() && internetConnectionControl.isInternetAvailable()) {
                request.newBuilder()
                    .cacheControl(
                        CacheControl.Builder().maxStale(CACHE_DURATION_MIN, TimeUnit.MINUTES)
                            .build()
                    )
                    .build()
            } else {
                // for offline
                request.newBuilder()
                    .cacheControl(
                        CacheControl.Builder().onlyIfCached()
                            .maxStale(
                                STALE_DURATION_DAYS,
                                TimeUnit.DAYS
                            )
                            .build()
                    )
                    .build()
            }
        chain.proceed(request)
    }

    @Provides
    @Singleton
    fun provideConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideClient(
        @ApiInterceptor apiKeyInterceptor: Interceptor,
        @HostInterceptor hostInterceptor: Interceptor,
        @InternetInterceptor internetInterceptor: Interceptor,
        @CacheInterceptor cacheInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ) = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(hostInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(internetInterceptor)
        .addNetworkInterceptor(cacheInterceptor)
        .connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        .cache(cache)
        .build()

    @Provides
    @Singleton
    fun provideMealsApi(retrofit: Retrofit): MealsApi = retrofit
        .create(MealsApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(
        okhttp: OkHttpClient,
        converterFactory: GsonConverterFactory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okhttp)
        .addConverterFactory(converterFactory)
        .build()

    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val cacheDir: File = File(context.cacheDir, HTTP_CACHE_DIR);
        return Cache(cacheDir, HTTP_CACHE_MAX_SIZE.toLong())
    }

    companion object {
        private const val HTTP_CACHE_MAX_SIZE = 1 * 1024 * 1024
        private const val HTTP_CACHE_DIR = "http_cache_dir"

        private const val BASE_URL = "https://tasty.p.rapidapi.com/"
        private const val API_KEY = BuildConfig.API_KEY
        private const val API_KEY_HEADER = "X-RapidAPI-Key"
        private const val HOST = "tasty.p.rapidapi.com"
        private const val HOST_HEADER = "X-RapidAPI-Host"


        private const val TIMEOUT = 10
        private const val CACHE_DURATION_MIN = 10
        private const val CACHE_DURATION_SEC: Long = 600
        private const val STALE_DURATION_DAYS = 7
    }
}
