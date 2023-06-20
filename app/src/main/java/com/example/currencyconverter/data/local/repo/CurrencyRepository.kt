package com.example.currencyconverter.data.local.repo

import android.util.Log
import com.example.currencyconverter.data.local.base.BaseRealmRepository
import com.example.currencyconverter.data.local.model.Balance
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.extensions.realmTransaction
import io.realm.Realm
import java.util.UUID

class CurrencyRepository(realm: Realm) : BaseRealmRepository<Currency>(realm) {

    fun getBalancesForConversion(): ArrayList<Currency> {
        val currencies = queryBuilder().findAll()
        return ArrayList(currencies)
    }

    fun filterData(fromCurrency: String, toCurrency: String): Pair<Currency?, Currency?> {
        val from = queryBuilder().equalTo("name", fromCurrency).findFirst()
        val to = queryBuilder().equalTo("name", toCurrency).findFirst()
        return Pair(from, to)
    }

    fun insertCurrency(currency: Currency) {
        realmTransaction { realm ->
            val existingCurrency = queryBuilder()
                .equalTo("name", currency.name)
                .findFirst()
            try {
                if (existingCurrency == null) {
                    realm.insert(currency)
                }
            } catch (e: Exception) {
                Log.d("dbInsertError", e.message.toString())
            }
        }
    }

    fun updateCurrency(currency: Currency) {
        realmTransaction { realm ->
            val existingCurrency = queryBuilder()
                .equalTo("name", currency.name)
                .findFirst()

            try {
                if (existingCurrency != null) {
                    existingCurrency.name = currency.name
                    existingCurrency.amount = currency.amount
                    realm.insertOrUpdate(existingCurrency)
                }
            } catch (e: Exception) {
                Log.d("dbUpdateError", e.message.toString())
            }
        }
    }

    fun incrementCounter() {
        realmTransaction { transactionRealm ->
            val counter = transactionRealm.where(Balance::class.java).findFirst()
                ?: transactionRealm.createObject(Balance::class.java, UUID.randomUUID().toString())
            counter.value++
            transactionRealm.copyToRealmOrUpdate(counter)
        }
    }

    fun getCounterNumber(): Int {
        return try {
            val counter = realm.where(Balance::class.java).findFirst()
            counter?.value ?: 0
        } catch (e: Exception) {
            0
        }
    }

    override fun getPersistentType() = Currency::class.java

}