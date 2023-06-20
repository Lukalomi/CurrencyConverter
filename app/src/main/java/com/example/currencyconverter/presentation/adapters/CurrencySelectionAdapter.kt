package com.example.currencyconverter.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import com.example.currencyconverter.R
import com.example.currencyconverter.data.local.model.Currency
import com.example.currencyconverter.presentation.adapters.diffutils.CurrencyDiffCallback
import com.example.currencyconverter.presentation.base.BaseListAdapter
import kotlinx.android.synthetic.main.currencyselection_item.view.tvCurrencyName


class CurrencySelectionAdapter(
    override var items: ArrayList<Currency>,
    private val onClickAction: (Currency) -> Unit?
) :
    BaseListAdapter<Currency>(items) {

    private var filteredItems: ArrayList<Currency> = items


    override fun onBindViewHolder(view: View, item: Currency, position: Int) {
        with(view) {
            tvCurrencyName.text = item.name
            view.setOnClickListener {
                onClickAction(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.currencyselection_item, parent, false)
        return ViewHolder(view)
    }

    fun submitList(newList: ArrayList<Currency>) {
        val diffResult = DiffUtil.calculateDiff(CurrencyDiffCallback(items, newList))
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }
}