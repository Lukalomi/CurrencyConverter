package com.example.currencyconverter.presentation.currencyselection

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.data.local.repo.CurrencyRepository
import com.example.currencyconverter.extensions.realm
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CurrencySelectionManager @Inject constructor() {

    @RequiresApi(Build.VERSION_CODES.N)
    fun getItemsFromDB(state: StateFlow<CurrencySelectionState>) {
        val tempList = mutableListOf<Currency>()
        val currencyList = realm { CurrencyRepository(it).getBalancesForConversion() }
        currencyList.removeIf { ignoredCurrency ->
            ignoredCurrency.name == (GlobalData.receiveCurrency?.selectedCurrency ?: "")
        }
        tempList.addAll(currencyList)
        state.value.listCurrencyUiModels.addAll(tempList)
    }


    fun addAllCurrencies(state: StateFlow<CurrencySelectionState>) {
        val tempList = ArrayList<Currency>()
        tempList.addAll(listOf(
            Currency().apply {
                name = "EUR"
                amount = 0
            },
            Currency().apply {
                name = "CAD"
                amount = 0
            },
            Currency().apply {
                name = "USD"
                amount = 0
            },
            Currency().apply {
                name = "GBP"
                amount = 0
            },
            Currency().apply {
                name = "ISK"
                amount = 0
            },
            Currency().apply {
                name = "PHP"
                amount = 0
            },
            Currency().apply {
                name = "DKK"
                amount = 0
            },
            Currency().apply {
                name = "HUF"
                amount = 0
            },
            Currency().apply {
                name = "CZK"
                amount = 0
            },
            Currency().apply {
                name = "AUD"
                amount = 0
            }, Currency().apply {
                name = "RON"
                amount = 0
            }, Currency().apply {
                name = "SEK"
                amount = 0
            },
            Currency().apply {
                name = "IDR"
                amount = 0
            },
            Currency().apply {
                name = "INR"
                amount = 0
            },
            Currency().apply {
                name = "BRL"
                amount = 0
            },
            Currency().apply {
                name = "RUB"
                amount = 0
            },
            Currency().apply {
                name = "SEK"
                amount = 0
            }, Currency().apply {
                name = "HRK"
                amount = 0
            },
            Currency().apply {
                name = "JPY"
                amount = 0
            },
            Currency().apply {
                name = "THB"
                amount = 0
            },
            Currency().apply {
                name = "CHF"
                amount = 0
            },
            Currency().apply {
                name = "SGD"
                amount = 0
            },
            Currency().apply {
                name = "PLN"
                amount = 0
            },
            Currency().apply {
                name = "BGN"
                amount = 0
            },
            Currency().apply {
                name = "CNY"
                amount = 0
            }, Currency().apply {
                name = "NOK"
                amount = 0
            }, Currency().apply {
                name = "NZD"
                amount = 0
            }, Currency().apply {
                name = "ZAR"
                amount = 0
            }, Currency().apply {
                name = "MXN"
                amount = 0
            }, Currency().apply {
                name = "SEK"
                amount = 0
            }, Currency().apply {
                name = "ILS"
                amount = 0
            },
            Currency().apply {
                name = "HKD"
                amount = 0
            }
        ).filterNot { ignoredCurrency ->
            ignoredCurrency.name == (GlobalData.sellCurrency?.selectedCurrency ?: "")
        })
        state.value.listCurrencyUiModels.addAll(tempList)
    }
}