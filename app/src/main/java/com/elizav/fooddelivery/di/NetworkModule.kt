package com.elizav.fooddelivery.di

import com.elizav.fooddelivery.BuildConfig
import com.elizav.fooddelivery.data.api.MealsApi
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
    fun provideApiKeyInterceptor() = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    @Provides
    @Singleton
    fun provideConvertFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideClient(
        apiKeyInterceptor: Interceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .also {
            it.addInterceptor(
                HttpLoggingInterceptor()
            )
        }
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
        private const val BASE_URL = "http://www.themealdb.com/"
        private const val API_KEY = BuildConfig.API_KEY
        private const val QUERY_API_KEY = ""
    }
}
