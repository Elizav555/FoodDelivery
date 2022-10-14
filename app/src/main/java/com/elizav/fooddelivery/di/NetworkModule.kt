package com.elizav.fooddelivery.di

import android.content.Context
import com.elizav.fooddelivery.BuildConfig
import com.elizav.fooddelivery.data.api.MealsApi
import com.elizav.fooddelivery.di.qualifiers.ApiInterceptor
import com.elizav.fooddelivery.di.qualifiers.HostInterceptor
import com.elizav.fooddelivery.di.qualifiers.OfflineInterceptor
import com.elizav.fooddelivery.di.qualifiers.OnlineInterceptor
import com.elizav.fooddelivery.ui.utils.InternetConnectionControl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Cache
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
    fun provideConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @OnlineInterceptor
    fun provideOnlineInterceptor() = Interceptor { chain ->
        val response = chain.proceed(chain.request())
        val maxAge = 60
        response.newBuilder()
            .header("Cache-Control", "public, max-age=$maxAge")
            .removeHeader("Pragma")
            .build()
    }

    @Provides
    @OfflineInterceptor
    fun provideOfflineInterceptor(internetConnectionControl: InternetConnectionControl) =
        Interceptor { chain ->
            var request = chain.request()
            if (!(internetConnectionControl.isNetworkConnected() && internetConnectionControl.isInternetAvailable())) {
                val maxStale = 60 * 60 * 24 * 30
                request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("Pragma")
                    .build()
            }
            chain.proceed(request)
        }

    @Provides
    @Singleton
    fun provideClient(
        @ApiInterceptor apiKeyInterceptor: Interceptor,
        @HostInterceptor hostInterceptor: Interceptor,
        @OnlineInterceptor onlineInterceptor: Interceptor,
        @OfflineInterceptor offlineInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ) = OkHttpClient.Builder()
        .addInterceptor(offlineInterceptor)
        .addNetworkInterceptor(onlineInterceptor)
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(hostInterceptor)
        .addInterceptor(httpLoggingInterceptor)
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
    }
}
