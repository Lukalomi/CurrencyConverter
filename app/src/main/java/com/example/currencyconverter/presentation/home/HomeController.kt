package com.example.currencyconverter.presentation.home

import android.view.LayoutInflater
import com.example.currencyconverter.presentation.base.BaseController
import com.example.currencyconverter.presentation.base.interfaces.IView
import com.example.currencyconverter.presentation.base.utils.extensions.homeDaggerComponent

class HomeController : BaseController<HomeState, HomeIntent, HomeViewModel>(),
    HomeDelegate {

    override fun inject() {
        homeDaggerComponent().inject(this)
    }

    override fun onCreateControllerView(inflater: LayoutInflater): IView<HomeState> {
        return HomeView(inflater.context).also {
            it.delegate = this
            viewModel.pushIntent(HomeIntent.Initialize)
        }
    }

    override fun performConversion() {
        viewModel.pushIntent(HomeIntent.PerformConversion)
    }

    override fun displayConversion() {
        viewModel.pushIntent(HomeIntent.DisplayConversion)
    }

    override fun openCurrencySelection() {
        viewModel.pushIntent(HomeIntent.OpenCurrencySelection)
    }
}