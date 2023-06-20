package com.example.currencyconverter.presentation.base.interfaces

interface IView<S : IState> {

    var previousState: S?

    /**
     * **IMPORTANT**: When overriding `super.render(state)` must be placed at the bottom
     *
     * Renders the given state
     *
     * @param state the state to render
     */
    fun render(state: S) {
        previousState = state
    }
}