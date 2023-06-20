package com.example.currencyconverter.presentation.di.conversion

import com.example.currencyconverter.presentation.home.HomeController
import dagger.Subcomponent


@ConversionScope
@Subcomponent(modules = [HomeModule::class])
interface HomeComponent {
    fun inject(homeController: HomeController)

    @Subcomponent.Builder
    interface Builder {
        fun build(): HomeComponent
    }
}