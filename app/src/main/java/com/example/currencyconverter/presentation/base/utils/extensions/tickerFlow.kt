package com.example.currencyconverter.presentation.base.utils.extensions

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

fun tickerFlow(period: Long, initialDelay: Long = 0L) = flow {
    delay(initialDelay)
    while (true) {
        emit(Unit)
        delay(period)
    }
}