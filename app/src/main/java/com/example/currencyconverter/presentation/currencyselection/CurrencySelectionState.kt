package com.example.currencyconverter.presentation.currencyselection

import androidx.annotation.StringRes
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.presentation.base.interfaces.IState

data class CurrencySelectionState(
    var listCurrencyUiModels: ArrayList<Currency> = arrayListOf(),
    @StringRes val error: Int = 0
) : IState