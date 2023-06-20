package com.example.currencyconverter.presentation.home

import com.example.currencyconverter.data.common.Resource
import com.example.currencyconverter.data.di.entity.Currencies
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.data.local.repo.CurrencyRepository
import com.example.currencyconverter.data.retrofit.CurrencyConverterApi
import com.example.currencyconverter.extensions.realm
import com.example.currencyconverter.extensions.realmTransaction
import com.example.currencyconverter.presentation.common.ConversionConstants.COMMISSION_FEE_PERCENTAGE
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeManager @Inject constructor(
    private val currencyApi: CurrencyConverterApi
) {

    suspend fun getRates(base: String): Resource<Currencies> {
        return try {
            val response = currencyApi.getCurrencyRates(base)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }


    fun getItemsFromDB(state: StateFlow<HomeState>) {
        val tempList = mutableListOf<Currency>()
        val currencyList = realm { CurrencyRepository(it).getBalancesForConversion() }
        tempList.addAll(currencyList)
        state.value.currencyList.addAll(tempList)
    }

    fun incrementCounter() {
        realm {
            CurrencyRepository(it).incrementCounter()
        }
    }

    suspend fun getCounterNumber(
        action: suspend (count: Int) -> Unit
    ) {
        val counterNumber = realm {
            CurrencyRepository(it).getCounterNumber()
        }
        action(counterNumber)
    }

    fun balanceCheckValidation(
        fromCurrency: String,
        toCurrency: String,
        amountPrice: String
    ): Boolean {
        return try {
            val (from, _) = realm { CurrencyRepository(it).filterData(fromCurrency, toCurrency) }
            from != null && from.amount.toDouble() - amountPrice.toInt() * COMMISSION_FEE_PERCENTAGE >= amountPrice.toInt() && from.name == fromCurrency
        } catch (e: Exception) {
            return false
        }
    }

    fun updateCurrency(name: String, newAmount: Int, currencyList: MutableList<Currency>) {
        realmTransaction { realm ->
            val currencyIndex = currencyList.indexOfFirst { it.name == name }
            if (currencyIndex != -1) {
                val updatedCurrency = currencyList[currencyIndex].apply {
                    this.amount = newAmount
                    this.name = name
                }
                currencyList[currencyIndex] = updatedCurrency
                realm.insertOrUpdate(updatedCurrency)
            }
        }
    }

    fun insertCurrency(name: String, amount: Int, currencyList: MutableList<Currency>) {
        realmTransaction { realm ->
            val currency = Currency().apply {
                this.name = name
                this.amount = amount
            }
            realm.insert(currency)
            currencyList.add(currency)
        }
    }

    suspend fun filterDataAndCollect(
        fromCurrency: String,
        toCurrency: String,
        action: suspend (from: Currency?, to: Currency?) -> Unit
    ) {
        val (from, to) = realm { CurrencyRepository(it).filterData(fromCurrency, toCurrency) }
        action(from, to)
    }
}