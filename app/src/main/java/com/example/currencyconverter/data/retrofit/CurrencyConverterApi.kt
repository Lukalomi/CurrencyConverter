package com.example.currencyconverter.data.retrofit

import com.example.currencyconverter.data.common.EndPoints
import com.example.currencyconverter.data.di.entity.Currencies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyConverterApi {
    @GET(EndPoints.getCurrencies)
    suspend fun getCurrencyRates(@Query("base") base: String): Response<Currencies>
}