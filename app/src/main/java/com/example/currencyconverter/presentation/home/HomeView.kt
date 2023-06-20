package com.example.currencyconverter.presentation.home

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.extensions.afterTextChanged
import com.example.currencyconverter.extensions.closeKeyboard
import com.example.currencyconverter.presentation.adapters.CurrencyListAdapter
import com.example.currencyconverter.presentation.base.interfaces.IView
import com.example.currencyconverter.presentation.currencyselection.GlobalData
import com.example.currencyconverter.presentation.currencyselection.SecondScreen
import kotlinx.android.synthetic.main.view_home.view.btnConvert
import kotlinx.android.synthetic.main.view_home.view.etReceive
import kotlinx.android.synthetic.main.view_home.view.etSell
import kotlinx.android.synthetic.main.view_home.view.pbLoader
import kotlinx.android.synthetic.main.view_home.view.rvCurrencies
import kotlinx.android.synthetic.main.view_home.view.tvCurrencyReceive
import kotlinx.android.synthetic.main.view_home.view.tvCurrencySell

class HomeView(context: Context) : IView<HomeState>, ConstraintLayout(context) {
    override var previousState: HomeState? = null
    var delegate: HomeDelegate? = null
    private lateinit var currenciesAdapter: CurrencyListAdapter

    init {
        inflate(context, R.layout.view_home, this)
        setListeners()
    }

    override fun render(state: HomeState) {
        with(state) {
            initStates(state)
            setAdapter(state)
            etSell.afterTextChanged {
                previousState?.amountPrice = etSell.text.toString()
                delegate?.displayConversion()
            }
        }
        super.render(state)
    }

    private fun setAdapter(currencyList: HomeState) {
        currenciesAdapter = CurrencyListAdapter(currencyList.currencyList)
        currenciesAdapter.items = currencyList.currencyList
        rvCurrencies.layoutManager = LinearLayoutManager(
            context, LinearLayoutManager.HORIZONTAL, false
        )
        rvCurrencies.adapter = currenciesAdapter
        currenciesAdapter.submitList(currencyList.currencyList)
    }

    private fun initStates(state: HomeState) {
        with(state) {
            previousState?.amountPrice = etSell.text.toString()
            amountPrice = etSell.text.toString()
            fromCurrency =
                GlobalData.sellCurrency?.selectedCurrency ?: resources.getString(R.string.empty)
            tvCurrencySell.text = fromCurrency
            toCurrency =
                GlobalData.receiveCurrency?.selectedCurrency ?: resources.getString(R.string.empty)
            tvCurrencyReceive.text = toCurrency
            etReceive.text = convertedPrice
            pbLoader.isVisible = isLoadingStatus
        }
    }

    private fun setListeners() {
        btnConvert.setOnClickListener {
            delegate?.performConversion()
            closeKeyboard()
        }
        tvCurrencySell.setOnClickListener {
            SecondScreen.receivedScreen.SelectionScreen = false
            delegate?.openCurrencySelection()
        }
        tvCurrencyReceive.setOnClickListener {
            SecondScreen.receivedScreen.SelectionScreen = true
            delegate?.openCurrencySelection()
        }
    }
}