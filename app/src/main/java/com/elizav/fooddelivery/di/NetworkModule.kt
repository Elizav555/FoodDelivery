package com.elizav.fooddelivery.di

import com.elizav.fooddelivery.BuildConfig
import com.elizav.fooddelivery.data.api.MealsApi
import com.elizav.fooddelivery.di.qualifiers.ApiInterceptor
import com.elizav.fooddelivery.di.qualifiers.HostInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
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
    @Singleton
    fun provideClient(
        @ApiInterceptor apiKeyInterceptor: Interceptor,
        @HostInterceptor hostInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(hostInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .cache(null)
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

    companion object {
        private const val BASE_URL = "https://tasty.p.rapidapi.com/"
        private const val API_KEY = BuildConfig.API_KEY
        private const val API_KEY_HEADER = "X-RapidAPI-Key"
        private const val HOST = "tasty.p.rapidapi.com"
        private const val HOST_HEADER = "X-RapidAPI-Host"
    }
}
