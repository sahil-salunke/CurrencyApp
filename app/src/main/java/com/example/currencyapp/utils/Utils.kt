package com.example.currencyapp.utils

import android.accounts.NetworkErrorException
import com.example.currencyapp.presentation.AuthenticationException
import com.example.currencyapp.presentation.State
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {

        // function to manage errors
        fun resolveError(e: Exception): State.ErrorState {
            var error = e

            when (e) {
                is SocketTimeoutException -> {
                    error = NetworkErrorException("connection error!")
                }
                is ConnectException -> {
                    error = NetworkErrorException("no internet access!")
                }
                is UnknownHostException -> {
                    error = NetworkErrorException("no internet access!")
                }
            }
            if (e is HttpException) {
                when (e.code()) {
                    502 -> {
                        error = NetworkErrorException(e.code().toString())
                    }
                    401 -> {
                        throw AuthenticationException("authentication error!")
                    }
                    400 -> {
                        error = NetworkErrorException(e.code().toString())
                    }
                }
            }
            return State.ErrorState(error)
        }

        // function to get last three dates
        fun getLastTreeDates(): Array<String?> {
            val arr = arrayOfNulls<String>(3)
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val cal = Calendar.getInstance()
            for (num in 0..2) {
                cal.add(Calendar.DATE, -1)
                arr[num] = sdf.format(cal.time)
            }
            return arr
        }

    }
}