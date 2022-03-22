package com.example.currencyapp.model

/**
 * Data class for country currency names
 * @author Sahil Salunke
 * @since 21/3/2022
 */
data class CurrencyNames(
    val success: Boolean,
    val symbols: LinkedHashMap<String, String>
)