package com.example.currencyconverter.data.local.base

import android.content.Context
import android.util.Log
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.data.local.model.SeedData
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.internal.Util
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealmConfigurator @Inject constructor(private val context: Context) {

    fun initRealm() {
        Realm.init(context)
        val configurationBuilder =
            RealmConfiguration.Builder().allowQueriesOnUiThread(true).allowWritesOnUiThread(true)
                .modules(
                    Realm.getDefaultModule(), RealmModule()
                ).schemaVersion(101).initialData { realm ->
                    realm.insertOrUpdate(initialData.currencies)
                }


        val configuration = configurationBuilder.build()
        Realm.setDefaultConfiguration(configuration)

        var realmInstance: Realm? = null

        try {
            realmInstance = Realm.getInstance(configuration)
        } catch (exception: Throwable) {
            Log.d("exception", exception.message.toString())

            if (realmInstance == null) {
            } else if (!realmInstance.isClosed) {
                realmInstance.close()
            }
            try {
                Realm.deleteRealm(configuration)
            } catch (exception: Throwable) {
                val canonicalPath = configuration.path
                val realmFolder = configuration.realmDirectory
                val realmFileName = configuration.realmFileName
                Util.deleteRealm(canonicalPath, realmFolder, realmFileName)
            }

            Realm.getInstance(configuration)
        }
    }

    private val initialData = SeedData(
        currencies = arrayListOf(
            Currency().apply {
                name = "EUR"
                amount = 1000
            }
        )
    )
}