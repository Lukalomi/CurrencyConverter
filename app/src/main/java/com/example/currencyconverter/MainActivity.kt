package com.example.currencyconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.example.currencyconverter.presentation.base.conductor.RoutingActivity
import com.example.currencyconverter.presentation.home.HomeController
import kotlinx.android.synthetic.main.activity_main.controller_container

class MainActivity : AppCompatActivity(R.layout.activity_main), RoutingActivity {

    override lateinit var mainRouter: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainRouter = Conductor.attachRouter(this, controller_container, savedInstanceState)
        if (!mainRouter.hasRootController()) {
            mainRouter.setRoot(RouterTransaction.with(HomeController()))
        }
    }

    override fun onBackPressed() {
        if (!mainRouter.handleBack()) {
            super.onBackPressed()
        }
    }
}