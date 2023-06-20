package com.example.currencyconverter.presentation.currencyselection

import com.example.currencyconverter.presentation.base.BaseViewModel
import javax.inject.Inject

class CurrencySelectionViewModel @Inject constructor(
    private val router: CurrencySelectionRouter,
    private val currencySelectionManager: CurrencySelectionManager
) : BaseViewModel<CurrencySelectionState, CurrencySelectionIntent>() {

    override fun getInitialData() = CurrencySelectionState()

    override suspend fun handleIntent(intent: CurrencySelectionIntent) {
        when (intent) {
            is CurrencySelectionIntent.Initialize -> {
                populateTheCurrencySelection()
            }

            is CurrencySelectionIntent.CloseSell -> {
                router.popCurrentController()
            }

            is CurrencySelectionIntent.CloseReceived -> {
                router.popCurrentController()
            }

            is CurrencySelectionIntent.FilterCurrencies -> {
                filterCurrencies(intent.query)
            }
        }
    }

    private suspend fun filterCurrencies(query: String) {
        val filteredCurrencies = if (query.isNotEmpty()) {
            ArrayList(state.value.listCurrencyUiModels.filter { currency ->
                currency.name.contains(query, ignoreCase = true)
            })
        } else {
            ArrayList(state.value.listCurrencyUiModels)
        }
        updateState { it.copy(listCurrencyUiModels = filteredCurrencies) }
    }

    private fun populateTheCurrencySelection() {
        if (SecondScreen.receivedScreen.SelectionScreen) {
            addAllCurrencies()
        } else {
            fetchItemsFromDB()
        }
    }

    private fun fetchItemsFromDB() {
        currencySelectionManager.getItemsFromDB(state)
    }

    private fun addAllCurrencies() {
        currencySelectionManager.addAllCurrencies(state)
    }
}