package com.example.currencyconverter.presentation.currencyselection

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyconverter.R
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.extensions.inflateAndSetCurrencySearchMenu
import com.example.currencyconverter.extensions.setToolbarTitle
import com.example.currencyconverter.presentation.base.BaseController
import com.example.currencyconverter.presentation.base.interfaces.IView
import com.example.currencyconverter.presentation.base.utils.extensions.currencySelectionComponent

class CurrencySelectionController :
    BaseController<CurrencySelectionState, CurrencySelectionIntent, CurrencySelectionViewModel>(),
    CurrencySelectionDelegate {

    override fun inject() {
        currencySelectionComponent().inject(this)
    }

    override fun onCreateControllerView(inflater: LayoutInflater): IView<CurrencySelectionState> {
        (activity as AppCompatActivity).setToolbarTitle(R.string.balance_selection)

        setHasOptionsMenu(true)
        return CurrencySelectionView(inflater.context).also {
            it.delegate = this
            viewModel.pushIntent(CurrencySelectionIntent.Initialize)

        }
    }

    override fun onCurrencyUiModelClicked(currencyUiModel: Currency) {
        GlobalData.sellCurrency = SentData(currencyUiModel.name)
        viewModel.pushIntent(CurrencySelectionIntent.CloseSell(currencyUiModel))
    }

    override fun onCurrencyUiModelClickedReceive(currencyUiModel: Currency) {
        GlobalData.receiveCurrency = SentData(currencyUiModel.name)
        viewModel.pushIntent(CurrencySelectionIntent.CloseReceived(currencyUiModel))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflateAndSetCurrencySearchMenu(R.menu.menu, menu, viewModel)
        super.onCreateOptionsMenu(menu, inflater)
    }
}