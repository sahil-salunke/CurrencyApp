package com.example.currencyapp.network

import com.example.currencyapp.IConstants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class to make api calls using retrofit
 * @author Sahil Salunke
 * @since 16/3/2022
 */
object RetrofitBuilder {

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(IConstants.baseURL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .create()
                )
            )
            // we need to add converter factory to convert JSON object to Java object
            .build()
    }

    val currencyService = getInstance().create(CurrencyService::class.java)

}