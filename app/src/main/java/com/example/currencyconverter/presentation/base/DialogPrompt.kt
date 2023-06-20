package com.example.currencyconverter.presentation.base

import androidx.annotation.StringRes
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.base.utils.ActivityManager
import com.example.currencyconverter.presentation.base.utils.DialogHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DialogPrompt @Inject constructor(
    override var activityManager: ActivityManager,
    ) : BaseDialogPrompt(activityManager) {

    fun showDialog(
        @StringRes stringRes: Int,
        chargedAmount: String = "",
        fromCurrency:String = "",
        incomingAmount: String = "",
        toCurrency:String = "",
        feeAmount: String = "",
        feeCurrency:String = "",
        onDismiss: () -> Unit = {}
    ) {
        getActivity()?.apply {
            DialogHelper.showActionDialog(
                this,
                dialogMessage = getString(
                    stringRes,
                    chargedAmount,
                    fromCurrency,
                    incomingAmount,
                    toCurrency,
                    feeAmount,
                    feeCurrency
                ),
                positiveText = getString(R.string.dialog_btn),
                onDismiss = onDismiss
            )
        }
    }
}