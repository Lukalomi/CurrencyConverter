package com.example.currencyconverter.presentation.di.conversion

import androidx.lifecycle.ViewModel
import com.example.currencyconverter.data.di.AdapterModule
import com.example.currencyconverter.data.di.UtilModule
import com.example.currencyconverter.presentation.base.di.ViewModelKey
import com.example.currencyconverter.presentation.di.conversion.ConversionScope
import com.example.currencyconverter.presentation.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [UtilModule::class, AdapterModule::class])
abstract class HomeModule {

    @ConversionScope
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}