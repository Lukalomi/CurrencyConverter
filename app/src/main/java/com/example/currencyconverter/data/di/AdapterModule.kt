package com.example.currencyconverter.data.di

import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.presentation.adapters.CurrencyListAdapter
import com.example.currencyconverter.presentation.di.conversion.ConversionScope
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {

    @ConversionScope
    @Provides
    fun provideCurrencyListAdapter(currencyList: ArrayList<Currency>): CurrencyListAdapter {
        return CurrencyListAdapter(currencyList)
    }
}