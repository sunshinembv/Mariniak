package com.example.data.di

import com.example.data.network.MoviesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherForecastApi(): MoviesApi {
        val okHttpClient = OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).addInterceptor { chain ->
            val modifiedRequest =
                chain.request().newBuilder().addHeader(HEADER_API_KEY, API_KEY).build()
            chain.proceed(modifiedRequest)
        }.build()

        val retrofit = Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build()
        return retrofit.create()
    }

    companion object {
        private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
        private const val HEADER_API_KEY = "X-Api-Key"
        private const val API_KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"
    }
}