package com.example.currencyconverter.data.di

import android.content.Context
import com.example.currencyconverter.presentation.base.utils.ActivityManager
import com.example.currencyconverter.presentation.di.conversion.HomeComponent
import com.example.currencyconverter.presentation.di.selection.CurrencySelectionComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, UtilModule::class])
interface AppComponent {

    fun homeComponent(): HomeComponent.Builder

    fun currencySelectionComponent(): CurrencySelectionComponent.Builder

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun activityManager(activityManager: ActivityManager): Builder

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}