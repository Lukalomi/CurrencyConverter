package com.example.currencyconverter.presentation.di.selection

import com.example.currencyconverter.presentation.currencyselection.CurrencySelectionController
import dagger.Subcomponent


@CurrencySelectionScope
@Subcomponent(modules = [CurrencySelectionModule::class])
interface CurrencySelectionComponent {

    fun inject(currencySelectionController: CurrencySelectionController)

    @Subcomponent.Builder
    interface Builder {
        fun build(): CurrencySelectionComponent
    }
}