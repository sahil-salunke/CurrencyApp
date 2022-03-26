package com.example.currencyapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.domain.CurrencyUseCase
import com.example.currencyapp.presentation.State
import com.example.currencyapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel class to work as a bridge between UI and business logic
 * @author Sahil Salunke
 * @since 25/3/2022
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val currencyUseCase: CurrencyUseCase) :
    ViewModel() {

    private val _fromCurrenciesRates: MutableLiveData<List<String>> = MutableLiveData()
    val fromCurrencies: LiveData<List<String>>
        get() = _fromCurrenciesRates

    private val _toCurrenciesRates: MutableLiveData<List<String>> = MutableLiveData()
    val toCurrencies: LiveData<List<String>>
        get() = _toCurrenciesRates

    // Currency rates values
    private var rates: LinkedHashMap<String, Double>? = null

    private lateinit var fromCurrency: String
    private lateinit var toCurrency: String

    var fromValue: MutableLiveData<Double> = MutableLiveData(1.0)
    var toValue: MutableLiveData<Double> = MutableLiveData(1.0)

    private var fromCurrencyRate: MutableLiveData<Double> = MutableLiveData(1.0)
    private var toCurrencyRate: MutableLiveData<Double> = MutableLiveData(1.0)

    private fun getCurrencyResponse() = flow {
        emit(State.LoadingState)
        try {
            delay(1000)
            emit(State.DataState(currencyUseCase()))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Utils.resolveError(e))
        }
    }

    /**
     * Method to call api of currencies and manage response
     */
    fun getCurrencyData() {
        viewModelScope.launch {
            delay(500)
            getCurrencyResponse()
                .collect {
                    when (it) {
                        is State.DataState -> {
                            Log.d("Success", "${it.data.rates.keys.toList()}")
                            _fromCurrenciesRates.value = it.data.rates.keys.toList()
                            _toCurrenciesRates.value = it.data.rates.keys.toList()
                            rates = it.data.rates
                        }
                        is State.ErrorState -> Log.d("Error", "${it.exception}")
                        is State.LoadingState -> "loading"
                    }
                }
        }
    }

    /**
     * Method to set from currency rate
     */
    fun onFromSelectedItem(selected: String) {
        fromCurrency = selected
        fromCurrencyRate.value = rates?.get(fromCurrency)!!
    }

    /**
     * Method to set to currency rate
     */
    fun onToSelectedItem(selected: String) {
        toCurrency = selected
        toCurrencyRate.value = rates?.get(toCurrency)!!
    }

    /**
     * Method to convert currency
     */
    fun onFromInputValue(amount: Double?) {
        fromValue.value = Utils.getConvertedCurrency(
            toCurrencyRate.value,
            fromCurrencyRate.value, amount
        )
    }

    /**
     * Method to convert currency
     */
    fun onToInputValue(amount: Double?) {
        toValue.value = Utils.getConvertedCurrency(
            fromCurrencyRate.value,
            toCurrencyRate.value, amount
        )
    }


}