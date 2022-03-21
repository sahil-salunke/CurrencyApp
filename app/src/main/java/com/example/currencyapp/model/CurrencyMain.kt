package com.example.currencyapp.model

/**
 * Data class for currency conversion
 * @author Sahil Salunke
 * @since 21/3/2022
 */
data class CurrencyMain(
    val base: String,
    val date: String,
    val rates: Rates,
    val success: Boolean,
    val timestamp: Int
)

