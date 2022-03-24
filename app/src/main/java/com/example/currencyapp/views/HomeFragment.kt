package com.example.currencyapp.views

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.currencyapp.databinding.FragmentHomeBinding
import com.example.currencyapp.network.RetrofitBuilder
import com.example.currencyapp.network.Status
import com.example.currencyapp.repository.HomeRepository
import com.example.currencyapp.viewmodel.HomeViewModel
import com.example.currencyapp.viewmodelfactory.HomeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap


/**
 * Class represents the currency conversion screen
 * @author Sahil Salunke
 * @since 15/3/2022
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    // Binder instance for home fragment
    private lateinit var binding: FragmentHomeBinding

    // ViewModel instance
    private lateinit var viewModel: HomeViewModel

    // Currency rates values
    private var rates: LinkedHashMap<String, Double>? = null

    // Currency symbols
    private var listOfCurrency = listOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrenciesList()
        getCurrency()
        init()
        getLastTreeDates()
    }

    private fun getLastTreeDates(): Array<String?> {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val arr = arrayOfNulls<String>(3)
        val cal = Calendar.getInstance()
        for (num in 1..3) {
            cal.add(Calendar.DATE, -num)
            arr[num] = sdf.format(cal.time)
        }
        return arr
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
     * Get currency symbols list
     */
    private fun getCurrenciesList() {
        viewModel.getCurrencySymbols().observe(viewLifecycleOwner) {
            it?.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        Log.d(
                            "APISUCCESS",
                            "getCurrenciesList: " + response.data?.body()?.symbols
                        )
                        response.data?.body()?.symbols?.let { it1 -> setAdapter(it1) }
                    }
                    Status.ERROR -> {
                        Log.d("ERROR", "getCurrenciesList: " + response.message)
                    }
                    Status.LOADING -> {
                        binding.progress.visibility = View.VISIBLE
                        Log.d("LOADING", "getCurrenciesList: LOADING")
                    }
                }
            }
        }
    }

    /**
     * Bind data to adapters
     */
    private fun setAdapter(symbols: LinkedHashMap<String, String>) {
        listOfCurrency = symbols.keys.toList() as ArrayList<String>
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_dropdown_item,
            symbols.keys.toList() as ArrayList<String>
        )
        binding.spFrom.adapter = adapter
        binding.spTo.adapter = adapter
        binding.progress.visibility = View.GONE
    }

    /**
     * Get currency rates
     */
    private fun getCurrency() {
        viewModel.getCurrency().observe(viewLifecycleOwner) {
            it?.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        Log.d(
                            "APISUCCESS",
                            "getCurrency: " + "base: " + response.data?.body()?.base
                                    + "\nrates: " + response.data?.body()?.rates
                        )
                        rates = response.data?.body()?.rates
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

    /**
     * Initializations and events handling
     */
    private fun init() {

        var fromCurrency: String
        var toCurrency: String
        var fromCurrencyRate = 1.0
        var toCurrencyRate = 1.0

        binding.spFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                fromCurrency = listOfCurrency[position]
                fromCurrencyRate = rates?.get(fromCurrency)!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                toCurrency = listOfCurrency[position]
                toCurrencyRate = rates?.get(toCurrency)!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.etAmountFrom.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_GO) {
                val fromValue = binding.etAmountFrom.text.toString().toDoubleOrNull()
                binding.etAmountTo.setText(
                    getConvertedCurrency(
                        fromCurrencyRate,
                        toCurrencyRate, fromValue
                    ).toString()
                )
            }
            false
        }

        binding.etAmountTo.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_GO) {
                val fromValue = binding.etAmountTo.text.toString().toDoubleOrNull()
                binding.etAmountFrom.setText(
                    getConvertedCurrency(
                        toCurrencyRate,
                        fromCurrencyRate, fromValue
                    ).toString()
                )
            }
            false
        }


    }

    /**
     * Function to hold logic of currency conversion
     */
    private fun getConvertedCurrency(
        fromCurrencyRate: Double,
        toCurrencyRate: Double,
        value: Double?
    ): Double {
        val inEuro = value?.div(fromCurrencyRate)
        return inEuro!!.times(toCurrencyRate)
    }

}