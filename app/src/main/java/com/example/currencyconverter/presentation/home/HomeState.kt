package com.example.currencyconverter.presentation.home

import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.presentation.base.interfaces.IState

data class HomeState(
    val isLoadingStatus: Boolean = false,
    var fromCurrency: String = "from",
    var toCurrency: String = "to",
    var amountPrice: String = "0",
    var convertedPrice: String? = null,
    var currencyList: ArrayList<Currency> = arrayListOf()
) : IState {
}