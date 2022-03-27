package com.example.currencyapp.domain

import com.example.currencyapp.data.CurrencyService
import com.example.currencyapp.data.containers.CurrencyMain
import javax.inject.Inject

/**
 * UseCase class to represent currency data api
 * @author Sahil Salunke
 * @since 25/3/2022
 */
class CurrencyUseCase @Inject constructor(
    private val apIs: CurrencyService
) {
    suspend operator fun invoke(): CurrencyMain {
        return apIs.getCurrency()
    }
}