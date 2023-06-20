package com.example.currencyconverter.presentation.base.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

object DialogHelper {

    fun showActionDialog(
        context: Context,
        dialogMessage: String,
        positiveText: String,
        onDismiss: () -> Unit = {}
    ) {
        MaterialDialog(context).show {
            message(text = dialogMessage)
            positiveButton(text = positiveText)
            setOnDismissListener { onDismiss() }
        }
    }
}