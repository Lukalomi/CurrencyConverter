package com.example.currencyconverter

import android.app.Application
import com.example.currencyconverter.data.di.Injector
import com.example.currencyconverter.data.local.base.RealmConfigurator
import com.example.currencyconverter.presentation.base.utils.ActivityManager
import com.example.currencyconverter.presentation.di.conversion.HomeComponent
import com.example.currencyconverter.presentation.di.conversion.HomeProvider
import com.example.currencyconverter.presentation.di.selection.CurrencySelectionComponent
import com.example.currencyconverter.presentation.di.selection.CurrencySelectionProvider
import io.realm.Realm

class App : Application(), HomeProvider, CurrencySelectionProvider {
    private lateinit var activityManager: ActivityManager
    private lateinit var realm: Realm

    companion object {
        lateinit var appContext: Application
    }

    override fun onCreate() {
        super.onCreate()
        val realmConfigurator = RealmConfigurator(applicationContext)
        appContext = this
        activityManager = ActivityManager()
        realmConfigurator.initRealm()
        realm = Realm.getDefaultInstance()
        registerActivityLifecycleCallbacks(activityManager.activityLifecycleCallbacks)
        Injector.initAppComponent(applicationContext, activityManager)
    }

    override fun provideHomeComponent(): HomeComponent {
        return Injector.appComponent.homeComponent().build()
    }

    override fun provideCurrencySelectionComponent(): CurrencySelectionComponent {
        return Injector.appComponent.currencySelectionComponent().build()
    }

}
