package com.example.currencyconverter.presentation.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseDialogPrompt constructor(
    protected open var activityManager: com.example.currencyconverter.presentation.base.utils.ActivityManager
) {
    protected fun getActivity(): AppCompatActivity? {
        return activityManager.currentActivity as? AppCompatActivity
    }
}