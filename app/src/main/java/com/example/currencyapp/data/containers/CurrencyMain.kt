package com.example.currencyapp.data.containers

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/**
 * Data class for currency conversion
 * @author Sahil Salunke
 * @since 21/3/2022
 */
data class CurrencyMain(
    val base: String? = null,
    val date: String,
    @Bindable
    val rates: LinkedHashMap<String, Double>,
    val success: Boolean? = null,
    val timestamp: Int? = null,
) : BaseObservable()

