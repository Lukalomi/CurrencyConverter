package com.example.currencyconverter.presentation.base.utils.extensions

import kotlinx.coroutines.channels.Channel

fun <T> Channel<T>.push(e: T) {
    try {
        trySend(e).isSuccess
    } catch (ignored: Throwable) {}
}