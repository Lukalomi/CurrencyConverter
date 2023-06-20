package com.example.currencyconverter.presentation.di.selection

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.data.di.AdapterModule
import com.example.currencyconverter.data.di.UtilModule
import com.example.currencyconverter.presentation.base.di.ViewModelKey
import com.example.currencyconverter.presentation.currencyselection.CurrencySelectionViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [UtilModule::class, AdapterModule::class])
abstract class CurrencySelectionModule {

    @CurrencySelectionScope
    @Binds
    @IntoMap
    @ViewModelKey(CurrencySelectionViewModel::class)
    abstract fun bindViewModel(viewModel: CurrencySelectionViewModel): ViewModel
}
