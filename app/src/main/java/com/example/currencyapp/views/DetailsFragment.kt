package com.example.currencyapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyapp.databinding.FragmentDetailsBinding
import com.example.currencyapp.network.RetrofitBuilder
import com.example.currencyapp.network.Status
import com.example.currencyapp.repository.HomeRepository
import com.example.currencyapp.viewmodel.HomeViewModel
import com.example.currencyapp.viewmodelfactory.HomeViewModelFactory
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


/**
 * Class represents the currency details screen
 * @author Sahil Salunke
 * @since 22/3/2022
 */
class DetailsFragment : Fragment() {

    // Binder instance for home fragment
    private lateinit var binding: FragmentDetailsBinding

    // ViewModel instance
    private lateinit var viewModel: HomeViewModel

    // Last three dates array
    private val arr = arrayOfNulls<String>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLastTreeDates()
        getLastThreeDaysData(arr[0].toString(), "INR,USD")
    }

    /**
     * View mode initialization at creation of fragment
     */
    private fun initializeViewModel() {
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(HomeRepository(RetrofitBuilder.networkConnection()))
        ).get(HomeViewModel::class.java)
    }

    /**
     * Get last three dates
     */
    private fun getLastTreeDates() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val cal = Calendar.getInstance()
        for (num in 0..2) {
            cal.add(Calendar.DATE, -1)
            arr[num] = sdf.format(cal.time)
        }
    }

    /**
     * Get last three days history
     */
    private fun getLastThreeDaysData(date: String, symbols: String) {
        viewModel.getLastThreeDaysData(date, symbols).observe(viewLifecycleOwner) {
            it?.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        Log.d(
                            "APISUCCESS",
                            "getCurrency: " + "base: " + response.data?.body()?.base
                                    + "\nrates: " + response.data?.body()?.rates
                        )
                        Log.d("APISUCCESS", "getCurrency: APISUCCESS")
                    }
                    Status.ERROR -> {
                        Log.d("ERROR", "getCurrency: " + response.message)
                    }
                    Status.LOADING -> {
                        Log.d("LOADING", "getCurrency: LOADING")
                    }
                }
            }
        }
    }

}