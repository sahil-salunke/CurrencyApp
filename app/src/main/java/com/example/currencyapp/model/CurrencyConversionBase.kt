package com.example.currencyapp.model


data class CurrencyConversionBase (

	val success : Boolean,
	val timestamp : Int,
	val base : String,
	val date : String,
	val rates : Rates
)