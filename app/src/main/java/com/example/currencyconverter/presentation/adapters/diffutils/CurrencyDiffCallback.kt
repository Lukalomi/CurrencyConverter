package com.example.currencyconverter.presentation.adapters.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.example.currencyconverter.data.local.model.Currency

class CurrencyDiffCallback(
    private val oldList: ArrayList<Currency>,
    private val newList: ArrayList<Currency>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].name == newList[newItemPosition].name
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}