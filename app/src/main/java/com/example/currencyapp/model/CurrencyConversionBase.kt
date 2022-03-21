package com.example.currencyapp.model

/**
 * Data class for currency conversion
 * @author Sahil Salunke
 * @since 16/3/2022
 */
data class CurrencyConversionBase(
    val success: Boolean,
    val timestamp: Int,
    val base: String,
    val date: String,
    val rates: Rates
)