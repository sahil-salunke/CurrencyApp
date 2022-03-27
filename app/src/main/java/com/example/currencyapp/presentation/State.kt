package com.example.currencyapp.presentation

/**
 * Sealed class to manage network states
 * @author Sahil Salunke
 * @since 24/3/2022
 */
sealed class State<out T> {
    object LoadingState : State<Nothing>()
    data class ErrorState(var exception: Throwable) : State<Nothing>()
    data class DataState<T>(var data: T) : State<T>()
}