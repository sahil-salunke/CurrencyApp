package com.example.currencyapp.presentation.views

import android.R
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.currencyapp.utils.EventListener
import com.example.currencyapp.databinding.FragmentHomeBinding
import com.example.currencyapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * Class represents the currency conversion screen
 * @author Sahil Salunke
 * @since 15/3/2022
 */
@AndroidEntryPoint
class HomeFragment : Fragment(), EventListener {

    // Binder instance for home fragment
    private lateinit var binding: FragmentHomeBinding

    // ViewModel instance
    private val viewModel: HomeViewModel by viewModels()

    // Currency rates values
    private var rates: LinkedHashMap<String, Double>? = null

    // Currency symbols
    private var listOfCurrency = listOf<String>()

    private lateinit var fromCurrency: String
    private lateinit var toCurrency: String
    private var fromCurrencyRate = 1.0
    private var toCurrencyRate = 1.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.listener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
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
     * Initializations and events handling
     */
    private fun init() {

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

//        binding.etAmountTo.setOnEditorActionListener { _, i, _ ->
//            if (i == EditorInfo.IME_ACTION_GO) {
//                val fromValue = binding.etAmountTo.text.toString().toDoubleOrNull()
//                binding.etAmountFrom.setText(
//                    getConvertedCurrency(
//                        toCurrencyRate,
//                        fromCurrencyRate, fromValue
//                    ).toString()
//                )
//            }
//            false
//        }


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

    override fun onItemSelected(view: View, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            when (view.id) {
                binding.etAmountFrom.id -> {
                    val fromValue = binding.etAmountFrom.text.toString().toDoubleOrNull()
                    binding.etAmountTo.setText(
                        getConvertedCurrency(
                            fromCurrencyRate,
                            toCurrencyRate, fromValue
                        ).toString()
                    )
                }
                binding.etAmountTo.id -> {
                    val fromValue = binding.etAmountFrom.text.toString().toDoubleOrNull()
                    binding.etAmountTo.setText(
                        getConvertedCurrency(
                            fromCurrencyRate,
                            toCurrencyRate, fromValue
                        ).toString()
                    )
                }
            }
        }
        return true
    }

    override fun onButtonClick() {
        findNavController().navigate(HomeFragmentDirections.actionHomeToDetails())

    }


}