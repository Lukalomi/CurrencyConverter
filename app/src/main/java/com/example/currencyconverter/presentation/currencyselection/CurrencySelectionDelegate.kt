package com.example.currencyconverter.presentation.currencyselection

import com.example.currencyconverter.data.local.model.Currency

interface CurrencySelectionDelegate {
    fun onCurrencyUiModelClicked(currencyUiModel: Currency)

    fun onCurrencyUiModelClickedReceive(currencyUiModel: Currency)

}