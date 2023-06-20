package com.example.currencyconverter.data.di

import com.example.currencyconverter.BuildConfig
import com.example.currencyconverter.data.retrofit.CurrencyConverterApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Singleton
    @Provides
    fun providesCurrencyApi(): CurrencyConverterApi =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(
            providesOkHttpClient()
        ).addConverterFactory(GsonConverterFactory.create()).build()
            .create(CurrencyConverterApi::class.java)
}
