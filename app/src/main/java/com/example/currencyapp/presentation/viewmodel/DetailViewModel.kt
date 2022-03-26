package com.example.currencyapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.currencyapp.domain.CurrencyUseCase
import com.example.currencyapp.domain.HistoryUseCase
import com.example.currencyapp.domain.SymbolsUseCase
import com.example.currencyapp.presentation.State
import com.example.currencyapp.utils.Utils
import com.example.currencyapp.utils.Utils.Companion.getLastTreeDates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * ViewModel class to work as a bridge between UI and business logic
 * @author Sahil Salunke
 * @since 26/3/2022
 */
@HiltViewModel
class DetailViewModel @Inject constructor(private val historyUseCase: HistoryUseCase) :
    ViewModel() {

    fun getHistoryResponse(dates: Array<String?> = getLastTreeDates(), symbols: String) =
        flow {
            emit(State.LoadingState)
            try {
                delay(1000)
                emit(State.DataState(historyUseCase(dates, symbols)))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Utils.resolveError(e))
            }
        }

}