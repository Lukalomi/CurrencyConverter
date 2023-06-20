package com.example.currencyconverter.presentation.currencyselection


data class SentData(val selectedCurrency: String)

data class SelectionScreen(var SelectionScreen: Boolean)

object GlobalData {
    var sellCurrency: SentData? = SentData("EUR")
    var receiveCurrency: SentData? = SentData("CAD")
}

object SecondScreen {
    var receivedScreen: SelectionScreen = SelectionScreen(false)
}