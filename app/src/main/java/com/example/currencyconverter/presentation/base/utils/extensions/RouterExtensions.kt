package com.example.currencyconverter.presentation.base.utils.extensions

import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction

fun Router.pushControllerVertically(controller: Controller) {
    pushController(RouterTransaction.with(controller).verticalChangeHandler())
}

fun Router.pushControllerWithoutAnimation(controller: Controller) {
    pushController(RouterTransaction.with(controller))
}