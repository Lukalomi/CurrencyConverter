package com.example.currencyconverter.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.R
import com.example.currencyconverter.data.common.Resource
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.extensions.withTwoZero
import com.example.currencyconverter.presentation.base.BaseViewModel
import com.example.currencyconverter.presentation.base.DialogPrompt
import com.example.currencyconverter.presentation.base.utils.StringPrompt
import com.example.currencyconverter.presentation.base.utils.extensions.tickerFlow
import com.example.currencyconverter.presentation.common.ConversionConstants.COMMISSION_FEE_PERCENTAGE
import com.example.currencyconverter.presentation.common.ConversionConstants.COMMISSION_FEE_TRANSACTION_COUNT
import com.example.currencyconverter.presentation.common.ConversionConstants.EXCHANGE_RATES_REFRESH_RATE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.round

class HomeViewModel @Inject constructor(
    private val router: HomeRouter,
    private val stringPrompt: StringPrompt,
    private val dialogPrompt: DialogPrompt,
    private val homeManager: HomeManager
) : BaseViewModel<HomeState, HomeIntent>() {

    override fun getInitialData(): HomeState = HomeState()

    override suspend fun handleIntent(intent: HomeIntent) {
        viewModelScope.launch {
            when (intent) {
                is HomeIntent.Initialize -> {
                    fetchItemsFromDB()
                    updateExchangesPeriodically()
                }

                is HomeIntent.PerformConversion -> {
                    performConversion()
                }

                is HomeIntent.DisplayConversion -> {
                    displayConversion()
                }

                is HomeIntent.OpenCurrencySelection -> {
                    router.openCurrencySelection()
                }
            }
        }
    }

    private suspend fun performConversion() {
        updateState { it.copy(isLoadingStatus = true) }
        conversionRates.collectLatest { result ->
            when (result) {
                is Resource.SuccessEvent -> {
                    updateState { it.copy(isLoadingStatus = false) }
                    val rate = getExchangeRate(
                        state.value.fromCurrency,
                        state.value.toCurrency,
                        result.resultText
                    )
                    val convertedCurrency = round(
                        state.value.amountPrice.toFloatOrNull()
                            ?.times(rate) ?: 0.0
                    )
                    convertedResult(convertedCurrency)
                }

                is Resource.FailureEvent -> {
                    dialogPrompt.showDialog(
                        R.string.failed_conversion
                    )
                    updateState { it.copy(isLoadingStatus = false) }
                }

                is Resource.LoaderEvent -> {
                    updateState { it.copy(isLoadingStatus = false) }
                }

                else -> Unit
            }
        }
    }

    private suspend fun updateExchangesPeriodically() {
        tickerFlow(EXCHANGE_RATES_REFRESH_RATE).collect {
            when (val ratesResponse = homeManager.getRates("EUR")) {
                is Resource.Error -> {
                    conversionRates.value = Resource.FailureEvent(ratesResponse.errorMessage)
                }

                is Resource.Loader -> {
                    conversionRates.value = Resource.LoaderEvent(ratesResponse.isLoading)
                }

                is Resource.Success -> {
                    val rates = ratesResponse.data.rates
                    conversionRates.value = Resource.SuccessEvent(rates)
                }
            }
        }
    }

    private val conversionRates =
        MutableStateFlow<Resource<Map<String, Double>>>(Resource.Empty(stringPrompt.getStringRes(R.string.empty)))

    private suspend fun convertedResult(result: Double) {
        with(state.value) {
            if (balanceCheckValidation()) {
                updateState { it.copy(convertedPrice = result.withTwoZero()) }
                homeManager.filterDataAndCollect(fromCurrency, toCurrency) { from, to ->
                    performCurrencyConversion(from, to, result)
                }
            } else {
                dialogPrompt.showDialog(
                    R.string.low_balance
                )
            }
        }
    }

    private suspend fun performCurrencyConversion(from: Currency?, to: Currency?, result: Double) {
        with(state.value) {
            homeManager.apply {
                if (from?.name == fromCurrency && to?.name != toCurrency) {
                    maxFreeConversionCheck(result)
                    updateCurrency(fromCurrency, from.amount - amountPrice.toInt(), currencyList)
                    insertCurrency(toCurrency, result.toInt(), currencyList)
                    incrementCounter()
                    updateState {
                        it.copy(
                            fromCurrency = fromCurrency,
                            amountPrice = (from.amount - amountPrice.toInt()).toString(),
                            toCurrency = toCurrency
                        )
                    }

                } else if (from?.name == fromCurrency && to?.name == toCurrency) {
                    maxFreeConversionCheck(result)
                    updateCurrency(toCurrency, to.amount + result.toInt(), currencyList)
                    updateCurrency(fromCurrency, from.amount - amountPrice.toInt(), currencyList)
                    incrementCounter()
                    updateState {
                        it.copy(
                            fromCurrency = fromCurrency,
                            amountPrice = (from.amount - amountPrice.toInt()).toString(),
                            toCurrency = toCurrency
                        )
                    }
                }
            }
        }
    }


    private fun getExchangeRate(
        from: String, to: String, rates: Map<String, Double>
    ): Double {
        val rateFrom = rates[from]
        val rateTo = rates[to]
        return if (rateFrom != null && rateTo != null) {
            rateFrom / rateTo
        } else {
            return 0.00
        }
    }


    private suspend fun maxFreeConversionCheck(
        result: Double,
    ) {
        homeManager.getCounterNumber {
            if (it < COMMISSION_FEE_TRANSACTION_COUNT) {
                with(state.value) {
                    dialogPrompt.showDialog(
                        R.string.success_tax_free,
                        amountPrice,
                        fromCurrency,
                        result.toString(),
                        toCurrency
                    )
                }
            } else {
                with(state.value) {
                    dialogPrompt.showDialog(
                        R.string.success_tax,
                        amountPrice,
                        fromCurrency,
                        result.withTwoZero(),
                        toCurrency,
                        (String.format(
                            "%.2f", amountPrice.toInt() * COMMISSION_FEE_PERCENTAGE
                        )),
                        fromCurrency
                    )
                }
            }
        }
    }

    private fun fetchItemsFromDB() {
        state.value.currencyList.clear()
        homeManager.getItemsFromDB(state)
    }

    private fun balanceCheckValidation(): Boolean {
        with(state.value) {
            return homeManager.balanceCheckValidation(
                fromCurrency,
                toCurrency,
                amountPrice
            )
        }
    }

    private suspend fun displayConversion() {
        updateState { it.copy(isLoadingStatus = true) }
        conversionRates.collect { result ->
            when (result) {

                is Resource.SuccessEvent -> {
                    updateState { it.copy(isLoadingStatus = false) }
                    val rate = getExchangeRate(
                        state.value.fromCurrency,
                        state.value.toCurrency,
                        result.resultText
                    )
                    val amountPrice = state.value.amountPrice
                    val convertedCurrency = round(
                        amountPrice.toFloatOrNull()
                            ?.times(rate) ?: 0.0
                    )
                    updateState {
                        it.copy(
                            convertedPrice = convertedCurrency.withTwoZero(),
                            amountPrice = amountPrice
                        )
                    }

                }

                is Resource.FailureEvent -> {
                    dialogPrompt.showDialog(
                        R.string.failed_conversion
                    )
                    updateState { it.copy(isLoadingStatus = false) }
                }

                is Resource.LoaderEvent -> {
                    updateState { it.copy(isLoadingStatus = false) }
                }

                else -> Unit
            }
        }
    }
}