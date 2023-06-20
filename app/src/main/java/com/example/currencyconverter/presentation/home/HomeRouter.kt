package com.example.currencyconverter.presentation.home

import com.example.currencyconverter.presentation.base.BaseRouter
import com.example.currencyconverter.presentation.base.utils.ActivityManager
import com.example.currencyconverter.presentation.currencyselection.CurrencySelectionController
import javax.inject.Inject

class HomeRouter @Inject constructor(
    activityManager: ActivityManager
) : BaseRouter(activityManager) {

    fun openCurrencySelection() {
        pushController(CurrencySelectionController(), PushAnimationDirection.VERTICALLY)
    }
}