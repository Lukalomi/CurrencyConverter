package com.example.currencyconverter.presentation.base.utils.extensions

import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.VerticalChangeHandler

private const val TRANSITION_DURATION = 200L

fun RouterTransaction.verticalChangeHandler(): RouterTransaction {
    this.pushChangeHandler(VerticalChangeHandler(TRANSITION_DURATION))
    this.popChangeHandler(VerticalChangeHandler(TRANSITION_DURATION))
    return this
}