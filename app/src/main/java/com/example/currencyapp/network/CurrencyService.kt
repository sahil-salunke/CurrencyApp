package com.example.currencyapp.network

import com.example.currencyapp.constants.IConstants
import com.example.currencyapp.model.CurrencyMain
import com.example.currencyapp.model.CurrencyNames
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to write api calls for retrofit
 * @author Sahil Salunke
 * @since 16/3/2022
 */
interface CurrencyService {

    @GET("symbols")
    suspend fun getCountrySymbols(@Query("access_key") accessKey: String = IConstants.accessKey)
            : Response<CurrencyNames>

    @GET("latest")
    suspend fun getCurrency(
        @Query("access_key") accessKey: String = IConstants.accessKey
    ): Response<CurrencyMain>

    public var date: String

    @GET()
    suspend fun getLastThreeDaysHistory(
        @Query("access_key") accessKey: String = IConstants.accessKey,
    ): Response<CurrencyMain>

}