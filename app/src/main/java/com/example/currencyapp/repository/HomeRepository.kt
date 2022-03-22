package com.example.currencyapp.repository

import com.example.currencyapp.network.CurrencyService
import javax.inject.Inject

/**
 * Repository class to hold business logic for home screen
 * @author Sahil Salunke
 * @since 15/3/2022
 */
class HomeRepository @Inject constructor(private val currencyService: CurrencyService) {

    suspend fun getCurrencySymbols() = currencyService.getCountrySymbols()
    suspend fun getCurrency() = currencyService.getCurrency()

}