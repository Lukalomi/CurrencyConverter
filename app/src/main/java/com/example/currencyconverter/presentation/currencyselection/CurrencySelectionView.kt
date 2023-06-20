package com.example.currencyconverter.presentation.currencyselection

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.adapters.CurrencySelectionAdapter
import com.example.currencyconverter.presentation.base.interfaces.IView
import kotlinx.android.synthetic.main.view_currencyselection.view.rvCurrencySelection

class CurrencySelectionView(context: Context) : IView<CurrencySelectionState>,
    ConstraintLayout(context) {

    override var previousState: CurrencySelectionState? = null
    var delegate: CurrencySelectionDelegate? = null
    private lateinit var currenciesAdapter: CurrencySelectionAdapter

    init {
        inflate(context, R.layout.view_currencyselection, this)
    }

    override fun render(state: CurrencySelectionState) {
        setAdapter(state)
        super.render(state)
    }

    private fun setAdapter(currencyList: CurrencySelectionState) {
        if (SecondScreen.receivedScreen.SelectionScreen) {
            currenciesAdapter = CurrencySelectionAdapter(currencyList.listCurrencyUiModels) {
                delegate?.onCurrencyUiModelClickedReceive(it)
            }
            currenciesAdapter.items = currencyList.listCurrencyUiModels
            rvCurrencySelection.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            rvCurrencySelection.adapter = currenciesAdapter
            currenciesAdapter.submitList(currencyList.listCurrencyUiModels)
        } else {
            currenciesAdapter = CurrencySelectionAdapter(currencyList.listCurrencyUiModels) {
                delegate?.onCurrencyUiModelClicked(it)
            }
            currenciesAdapter.items = currencyList.listCurrencyUiModels
            rvCurrencySelection.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
            rvCurrencySelection.adapter = currenciesAdapter
            currenciesAdapter.submitList(currencyList.listCurrencyUiModels)
        }
    }
}