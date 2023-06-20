package com.example.currencyconverter.data.di.entity

data class Currencies(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
