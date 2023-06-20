package com.example.currencyconverter.presentation.base.utils.extensions

import android.app.Activity
import com.bluelinelabs.conductor.Controller
import com.example.currencyconverter.presentation.base.conductor.RoutingActivity
import com.example.currencyconverter.presentation.di.conversion.HomeComponent
import com.example.currencyconverter.presentation.di.conversion.HomeProvider
import com.example.currencyconverter.presentation.di.selection.CurrencySelectionComponent
import com.example.currencyconverter.presentation.di.selection.CurrencySelectionProvider

fun Activity.asRoute(): RoutingActivity? {
    return this as? RoutingActivity
}

fun Controller.homeDaggerComponent(): HomeComponent {
    return (activity?.application as? HomeProvider)!!.provideHomeComponent()
}

fun Controller.currencySelectionComponent(): CurrencySelectionComponent {
    return (activity?.application as? CurrencySelectionProvider)!!.provideCurrencySelectionComponent()
}
