package com.example.currencyconverter.presentation.currencyselection

import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.presentation.base.interfaces.IIntent


sealed class CurrencySelectionIntent : IIntent {
    object Initialize : CurrencySelectionIntent()
    data class CloseSell(val exchangeRateUiModel: Currency) : CurrencySelectionIntent()

    data class CloseReceived(val exchangeRateUiModel: Currency) : CurrencySelectionIntent()

    data class FilterCurrencies(val query: String) : CurrencySelectionIntent()

}