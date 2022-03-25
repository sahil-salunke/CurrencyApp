package com.example.currencyapp.data

import com.example.currencyapp.utils.IConstants
import com.example.currencyapp.data.containers.CurrencyMain
import com.example.currencyapp.data.containers.CurrencyNames
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface to write api calls for retrofit
 * @author Sahil Salunke
 * @since 16/3/2022
 */
interface CurrencyService {

    @GET("symbols")
    suspend fun getCountrySymbols(@Query("access_key") accessKey: String = IConstants.accessKey)
            : CurrencyNames


    @GET("latest")
    suspend fun getCurrency(
        @Query("access_key") accessKey: String = IConstants.accessKey
    ): CurrencyMain


    @GET("{date}")
    suspend fun getLastThreeDaysHistory(
        @Path("date") date: String,
        @Query("access_key") accessKey: String,
        @Query("symbols") symbols: String
    ): CurrencyMain

}