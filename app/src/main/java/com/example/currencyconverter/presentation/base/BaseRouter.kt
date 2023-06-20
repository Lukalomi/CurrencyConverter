package com.example.currencyconverter.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Controller
import com.example.currencyconverter.presentation.base.utils.extensions.asRoute
import com.example.currencyconverter.presentation.base.utils.extensions.pushControllerVertically
import com.example.currencyconverter.presentation.base.utils.extensions.pushControllerWithoutAnimation

abstract class BaseRouter constructor(
    protected open var activityManager: com.example.currencyconverter.presentation.base.utils.ActivityManager
) {
    enum class PushAnimationDirection {
        VERTICALLY,
        NONE
    }

    fun pushController(
        controller: Controller,
        animationDirection: PushAnimationDirection = PushAnimationDirection.VERTICALLY
    ) {
        val router = getActivity()?.asRoute()?.mainRouter ?: return
        when (animationDirection) {
            PushAnimationDirection.VERTICALLY -> {
                router.pushControllerVertically(controller)
            }

            PushAnimationDirection.NONE -> {
                router.pushControllerWithoutAnimation(controller)
            }
        }
    }

    fun popCurrentController() {
        getActivity()?.asRoute()?.mainRouter?.let { router ->
            if (router.backstack.isEmpty()) {
                getActivity()?.onBackPressed()
            } else {
                router.popCurrentController()
            }
        }
    }

    private fun getActivity(): AppCompatActivity? {
        return activityManager.currentActivity as? AppCompatActivity
    }

}