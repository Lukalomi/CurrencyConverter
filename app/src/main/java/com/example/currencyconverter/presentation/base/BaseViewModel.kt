package com.example.currencyconverter.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.presentation.base.interfaces.IIntent
import com.example.currencyconverter.presentation.base.interfaces.IModel
import com.example.currencyconverter.presentation.base.interfaces.IState
import com.example.currencyconverter.presentation.base.utils.extensions.push
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : IState, I : IIntent> : ViewModel(), IModel<S, I> {

    override val intents = Channel<I>(Channel.UNLIMITED)
    override val state: StateFlow<S>
        get() = _state

    private val _state: MutableStateFlow<S>

    init {
        _state = MutableStateFlow(getInitialData())
        viewModelScope.launch {
            intents.consumeAsFlow().collect { intent ->
                handleIntent(intent)
            }
        }
    }

    protected abstract fun getInitialData(): S
    protected abstract suspend fun handleIntent(intent: I)

    protected suspend fun updateState(handler: suspend (intent: S) -> S) {
        _state.value = handler(state.value)
    }

    fun pushIntent(intent: I) {
        intents.push(intent)
    }
}