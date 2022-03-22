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
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.currencyapp.databinding.FragmentHomeBinding
import com.example.currencyapp.network.RetrofitBuilder
import com.example.currencyapp.network.Status
import com.example.currencyapp.repository.HomeRepository
import com.example.currencyapp.viewmodel.HomeViewModel
import com.example.currencyapp.viewmodelfactory.HomeViewModelFactory
import dagger.hilt.android.AndroidEntryPoint


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

    private var rates: LinkedHashMap<String, Double>? = null

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
        getCurrency("")
        init()
    }

    private fun init() {

        var fromCurrency: String = ""
        var toCurrency: String = ""
        var fromCurrencyRate: Double = 1.0
        var toCurrencyRate: Double = 1.0

        binding.spFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                fromCurrency = listofCurrency[position]
                fromCurrencyRate = rates?.get(fromCurrency)!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                toCurrency = listofCurrency[position]
                toCurrencyRate = rates?.get(toCurrency)!!
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.etAmountFrom.setOnEditorActionListener(OnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_GO) {
                var fromValue = binding.etAmountFrom.text.toString().toDoubleOrNull()
                binding.etAmountTo.setText(
                    "" + getConvertedCurrency(
                        fromCurrencyRate,
                        toCurrencyRate, fromValue
                    )
                )
            }
            false
        })

        binding.etAmountTo.setOnEditorActionListener(OnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_GO) {
                var fromValue = binding.etAmountTo.text.toString().toDoubleOrNull()
                binding.etAmountFrom.setText(
                    "" + getConvertedCurrency(
                        toCurrencyRate,
                        fromCurrencyRate, fromValue
                    )
                )
            }
            false
        })

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

    private fun getCurrenciesList() {
        viewModel.getCurrencySymbols().observe(viewLifecycleOwner, Observer {
            it?.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        Log.d(
                            "APISUCCESS",
                            "getCurrenciesList: " + response.data?.body()?.symbols
                        )
                        response.data?.body()?.symbols?.let { it1 -> setAdapter(it1) }

                        Toast.makeText(activity, "SUCCESS", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Log.d("ERROR", "getCurrenciesList: " + response.message)
                    }
                    Status.LOADING -> {
                        Log.d("LOADING", "getCurrenciesList: LOADING")
                    }
                }
            }
        })
    }

    private var listofCurrency = listOf<String>()

    private fun setAdapter(symbols: LinkedHashMap<String, String>) {
        listofCurrency = symbols.keys.toList() as ArrayList<String>
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.simple_spinner_item,
            symbols.keys.toList() as ArrayList<String>
        )
        binding.spFrom.adapter = adapter
        binding.spTo.adapter = adapter
    }

    private fun getCurrency(from: String) {
        viewModel.getCurrency(from).observe(viewLifecycleOwner, Observer {
            it?.let { response ->
                when (response.status) {
                    Status.SUCCESS -> {
                        Log.d(
                            "APISUCCESS",
                            "getCurrency: " + "base: " + response.data?.body()?.base
                                    + "\nrates: " + response.data?.body()?.rates
                        )
                        rates = response.data?.body()?.rates
                        Toast.makeText(activity, "SUCCESS", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Log.d("ERROR", "getCurrency: " + response.message)
                    }
                    Status.LOADING -> {
                        Log.d("LOADING", "getCurrency: LOADING")
                    }
                }
            }
        })
    }

    private fun getConvertedCurrency(
        fromCurrencyRate: Double,
        toCurrencyRate: Double,
        value: Double?
    ): Double {
        val inEuro = value?.div(fromCurrencyRate)
        return inEuro!!.times(toCurrencyRate)
    }

}