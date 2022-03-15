package com.example.currencyapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyapp.viewmodel.HomeViewModel

/**
 * Factory to manage context for home screen viewModel
 * @author Sahil Salunke
 * @since 15/3/2022
 */
class HomeViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("UnknownViewModel")
    }

}