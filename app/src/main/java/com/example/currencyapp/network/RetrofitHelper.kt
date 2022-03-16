package com.example.currencyapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
  
    private val baseUrl = "https://data.fixer.io/api/"
  
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to 
            // convert JSON object to Java object
            .build()
    }
}