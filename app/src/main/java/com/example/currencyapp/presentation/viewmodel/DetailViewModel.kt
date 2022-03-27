package com.example.currencyapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.data.containers.CurrencyHistoryDTO
import com.example.currencyapp.domain.HistoryUseCase
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
 * @since 26/3/2022
 */
@HiltViewModel
class DetailViewModel @Inject constructor(private val historyUseCase: HistoryUseCase) :
    ViewModel() {

    private val _fromCurrenciesRates: MutableLiveData<List<String>> = MutableLiveData()
    val fromCurrencies: LiveData<List<String>>
        get() = _fromCurrenciesRates

    // Currency rates values
    private var rates: LinkedHashMap<String, Double>? = null

    private val _currencyHistory: MutableLiveData<List<CurrencyHistoryDTO>> = MutableLiveData()
    val currencyHistory: LiveData<List<CurrencyHistoryDTO>>
        get() = _currencyHistory

    private val currencyHistoryList = mutableListOf<CurrencyHistoryDTO>()

    private fun getHistoryResponse(date: String, symbols: String) =
        flow {
            emit(State.LoadingState)
            try {
                delay(1000)
                emit(State.DataState(historyUseCase(date, symbols)))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    /**
     * Method to call api of currencies and manage response
     */
    fun getHistoryData(symbols: String) {
        val tempCurrency = symbols.split(",")
        val fromCurrency = tempCurrency[0]
        val toCurrency = tempCurrency[1]
        repeat(Utils.getLastTreeDates().size) { i ->
            viewModelScope.launch {
                delay(500)
                getHistoryResponse(Utils.getLastTreeDates()[i].toString(), symbols)
                    .collect {
                        when (it) {
                            is State.DataState -> {
                                Log.d("Success", "${it.data}")
                                rates = it.data.rates
                                currencyHistoryList.add(
                                    CurrencyHistoryDTO(
                                        fromCurrency, toCurrency,
                                        1.0,
                                        Utils.getConvertedCurrency(
                                            rates?.get(fromCurrency),
                                            rates?.get(toCurrency), 1.0
                                        ),
                                        it.data.date
                                    )
                                )
                                if (Utils.getLastTreeDates().size == currencyHistoryList.size) {
                                    val sorted =
                                        currencyHistoryList.sortedByDescending { d -> d.date }
                                    _currencyHistory.value = sorted

                                }
                            }
                            is State.ErrorState -> Log.d("Error", "${it.exception}")
                            is State.LoadingState -> "loading"
                        }
                    }
            }
        }
    }


}