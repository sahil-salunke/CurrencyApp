package com.example.currencyapp.data.containers

import androidx.databinding.BaseObservable

/**
 * Data class for currency history
 * @author Sahil Salunke
 * @since 27/3/2022
 */
data class CurrencyHistoryDTO(
    val fromCurrency: String? = null,
    val toCurrency: String? = null,
    val fromCurrencyValue: Double? = null,
    val toCurrencyValue: Double? = null,
    val date: String
) : BaseObservable()

