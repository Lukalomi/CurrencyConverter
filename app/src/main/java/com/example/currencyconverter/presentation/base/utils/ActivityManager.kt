package com.example.currencyconverter.presentation.base.utils

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

class ActivityManager {

    val activityLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            setActivity(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            setActivity(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            setActivity(activity)
        }

        override fun onActivityPaused(activity: Activity) {
            clearActivity(activity)
        }

        override fun onActivityStopped(activity: Activity) {
            clearActivity(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
        override fun onActivityDestroyed(activity: Activity) {
            clearActivity(activity)
        }
    }

    var currentActivity: Activity? = null
        private set

    private fun setActivity(activity: Activity) {
        if (currentActivity != activity) {
            currentActivity = activity
        }
    }

    private fun clearActivity(currentActivity: Activity) {
        if (this.currentActivity == currentActivity) {
            this.currentActivity = null
        }
    }
}