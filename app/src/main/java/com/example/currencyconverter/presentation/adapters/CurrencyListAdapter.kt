package com.example.currencyconverter.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.currencyconverter.R
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.presentation.adapters.diffutils.CurrencyDiffCallback
import com.example.currencyconverter.presentation.base.BaseListAdapter
import kotlinx.android.synthetic.main.single_item.view.tvAvailableBalance
import kotlinx.android.synthetic.main.single_item.view.tvCurrencyName

class CurrencyListAdapter(override var items: ArrayList<Currency>) :
    BaseListAdapter<Currency>(items) {


    override fun onBindViewHolder(view: View, item: Currency, position: Int) {
        with(view) {
            tvAvailableBalance.text = item.amount.toString()
            tvCurrencyName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.single_item, parent, false)
        return ViewHolder(view)
    }

    fun submitList(newList: ArrayList<Currency>) {
        val diffResult = DiffUtil.calculateDiff(CurrencyDiffCallback(items, newList))
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }
}