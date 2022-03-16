package com.example.currencyapp.network

import com.example.currencyapp.model.Rates
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {
    @GET("/symbols")
    suspend fun getCountryNames() : Response<Rates>

    @GET("/latest")
    suspend fun getCurrency() : Response<Rates>
}