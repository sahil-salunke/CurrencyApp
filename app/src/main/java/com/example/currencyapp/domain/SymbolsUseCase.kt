package com.example.currencyapp.domain

import com.example.currencyapp.data.CurrencyService
import com.example.currencyapp.data.containers.CurrencyNames
import javax.inject.Inject

/**
 * UseCase class to represent currency symbols data api
 * @author Sahil Salunke
 * @since 25/3/2022
 */
class SymbolsUseCase @Inject constructor(
    private val apIs: CurrencyService
) {
    suspend operator fun invoke(): CurrencyNames {
        return apIs.getCountrySymbols()
    }
}