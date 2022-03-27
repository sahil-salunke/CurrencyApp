package com.example.currencyapp.domain

import com.example.currencyapp.data.CurrencyService
import com.example.currencyapp.data.containers.CurrencyMain
import com.example.currencyapp.utils.IConstants
import javax.inject.Inject

/**
 * UseCase class to represent history data api
 * @author Sahil Salunke
 * @since 25/3/2022
 */
class HistoryUseCase @Inject constructor(
    private val apIs: CurrencyService
) {
    suspend operator fun invoke(date: String, symbols: String): CurrencyMain {
        return apIs.getLastThreeDaysHistory(date, IConstants.accessKey, symbols)
    }
}