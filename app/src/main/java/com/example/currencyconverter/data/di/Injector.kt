package com.example.currencyconverter.data.di

import android.content.Context
import com.example.currencyconverter.presentation.base.utils.ActivityManager

object Injector {

    internal lateinit var appComponent: AppComponent

    internal fun initAppComponent(context: Context,activityManager: ActivityManager) {
        appComponent = DaggerAppComponent.builder()
            .activityManager(activityManager)
            .context(context)
            .build()
    }
}