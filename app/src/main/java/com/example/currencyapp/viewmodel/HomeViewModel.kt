package com.example.currencyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.currencyapp.network.Resource
import com.example.currencyapp.repository.HomeRepository
import kotlinx.coroutines.Dispatchers

/**
 * ViewModel to manage things for home screen
 * @author Sahil Salunke
 * @since 15/3/2022
 */
class HomeViewModel(private val mainRepository: HomeRepository) : ViewModel() {

    fun getCurrencySymbols() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getCurrencySymbols()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}