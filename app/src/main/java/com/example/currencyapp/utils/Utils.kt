package com.example.currencyapp.utils

import android.accounts.NetworkErrorException
import com.example.currencyapp.data.containers.Rates
import com.example.currencyapp.presentation.AuthenticationException
import com.example.currencyapp.presentation.State
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

/**
 * Util class for common use logics
 * @author Sahil Salunke
 * @since 25/3/2022
 */
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
            val arr = arrayOfNulls<String>(4)
            val sdf = SimpleDateFormat(IConstants.DATE_FORMAT)
            val cal = Calendar.getInstance()
            arr[0] = sdf.format(cal.time)
            for (num in 1..3) {
                cal.add(Calendar.DATE, -1)
                arr[num] = sdf.format(cal.time)
            }
            return arr
        }

        // Function to hold logic of currency conversion
        fun getConvertedCurrency(
            fromCurrencyRate: Double?,
            toCurrencyRate: Double?,
            value: Double?
        ): Double {
            val inEuro = value?.div(fromCurrencyRate!!)
            return inEuro!!.times(toCurrencyRate!!)
        }

        // Function to convert Linked hash map to arraylist
        fun linkedHashMapToArrayList(hash: LinkedHashMap<String, Double>?): ArrayList<Rates> {
            val arrayList: ArrayList<Rates> = ArrayList()
            hash!!.forEach { (s, d) ->
                val rate = Rates()
                rate.currency = s
                rate.rate = d
                arrayList.add(rate)
            }
            val tempList: ArrayList<Rates> = ArrayList()
            for (i in 0 until 10) {
                tempList.add(arrayList[i])
            }
            return tempList
        }
    }
}