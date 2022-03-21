package com.example.currencyapp.network

import com.example.currencyapp.IConstants
import com.example.currencyapp.model.CurrencyMain
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Interface to write api calls for retrofit
 * @author Sahil Salunke
 * @since 16/3/2022
 */
interface CurrencyService {

    @GET("/symbols")
    suspend fun getCountrySymbols(@Query("access_key") accessKey: String = IConstants.accessKey)
            : Response<JSONObject>

    @GET("latest")
    suspend fun getCurrency(@Query("access_key") accessKey: String = IConstants.accessKey, @Query("format") format:Int = 1)
            : Response<CurrencyMain>

}