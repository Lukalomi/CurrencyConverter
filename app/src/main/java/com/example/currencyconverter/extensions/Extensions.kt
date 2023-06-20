package com.example.currencyconverter.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.currencyselection.CurrencySelectionIntent
import com.example.currencyconverter.presentation.currencyselection.CurrencySelectionViewModel
import java.text.DecimalFormat

fun Double.withTwoZero(): String {
    val doublePattern = DecimalFormat("#.##")
    return doublePattern.format(this)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun View.closeKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun AppCompatActivity.setToolbarTitle(stringResId: Int) {
    supportActionBar?.title = getString(stringResId)
}

fun MenuInflater.inflateAndSetCurrencySearchMenu(
    menuResId: Int,
    menu: Menu,
    viewModel: CurrencySelectionViewModel
) {
    inflate(menuResId, menu)
    val searchItem: MenuItem? = menu.findItem(R.id.action_search)
    val searchView: SearchView? = searchItem?.actionView as? SearchView
    searchView?.queryHint = "Search Currency"

    searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            viewModel.pushIntent(CurrencySelectionIntent.FilterCurrencies(newText))
            return false
        }
    })
}
