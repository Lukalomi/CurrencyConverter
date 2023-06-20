package com.example.currencyconverter.presentation.home

import com.example.currencyconverter.presentation.base.interfaces.IIntent

sealed class HomeIntent : IIntent {
    object Initialize : HomeIntent()
    object PerformConversion : HomeIntent()

    object DisplayConversion : HomeIntent()

    object OpenCurrencySelection: HomeIntent()
}