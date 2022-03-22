package com.example.currencyapp.network

import com.example.currencyapp.constants.IConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Class to make api calls using retrofit
 * @author Sahil Salunke
 * @since 16/3/2022
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitBuilder {

    @Singleton
    @Provides
    fun networkConnection(): CurrencyService = Retrofit.Builder().baseUrl(IConstants.baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(CurrencyService::class.java)
}