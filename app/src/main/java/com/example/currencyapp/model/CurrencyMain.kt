package com.example.currencyapp.model

/**
 * Data class for currency conversion
 * @author Sahil Salunke
 * @since 21/3/2022
 */
data class CurrencyMain(
    val base: String? = null,
    val date: String? = null,
    val rates: LinkedHashMap<String, Double>,
    val success: Boolean? = null,
    val timestamp: Int? = null,
)

