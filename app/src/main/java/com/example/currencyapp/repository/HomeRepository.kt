package com.example.currencyapp.repository

import com.example.currencyapp.constants.IConstants
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
    suspend fun getLastThreeDaysData(date: String, symbols: String) =
        currencyService.getLastThreeDaysHistory(
            date, IConstants.accessKey, symbols
        )

}