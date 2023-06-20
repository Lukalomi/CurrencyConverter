package com.example.currencyconverter.presentation.base.utils

import androidx.annotation.StringRes
import com.example.currencyconverter.presentation.base.BaseDialogPrompt
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringPrompt @Inject constructor(
    override var activityManager: ActivityManager
) : BaseDialogPrompt(activityManager) {

    fun getStringRes(
        @StringRes stringRes: Int,
    ):String {
        return activityManager.currentActivity?.getString(stringRes) ?: ""
    }
}