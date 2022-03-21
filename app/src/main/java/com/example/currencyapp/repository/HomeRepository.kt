package com.example.currencyapp.repository

import com.example.currencyapp.network.CurrencyService
import com.example.currencyapp.network.RetrofitBuilder

/**
 * Repository class to hold business logic for home screen
 * @author Sahil Salunke
 * @since 15/3/2022
 */
class HomeRepository constructor(private val currencyService: CurrencyService) {

    suspend fun getCurrencySymbols() = currencyService.getCurrency()

}
//
//class HomeRepository {
//
//    private var liveData: MutableLiveData<BaseCurrency>? = null
//
//    fun MainRepository() {
//        liveData = MutableLiveData<BaseCurrency>()
//    }
//
//    fun getSuperHeroes() {
//        val currencyApi = RetrofitHelper.getInstance().create(CurrencyApi::class.java)
//        currencyApi.enqueue(object : Callback<BaseCurrency> {
//            override fun onResponse(
//                call: Call<BaseCurrency>,
//                response: Response<BaseCurrency>
//            ) {
//                if (response.body() != null) {
//                    liveData!!.postValue(response.body())
//                }
//            }
//
//            override fun onFailure(call: Call<BaseCurrency>, t: Throwable) {
//                liveData!!.postValue(null)
//            }
//        })
//    }
//
//    fun getRepositoryLiveData(): LiveData<BaseCurrency>? {
//        return liveData
//    }
//
//
//}