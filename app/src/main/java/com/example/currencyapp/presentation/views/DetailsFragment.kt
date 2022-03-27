package com.example.currencyapp.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.currencyapp.data.containers.Rates
import com.example.currencyapp.databinding.FragmentDetailsBinding
import com.example.currencyapp.presentation.adapters.CurrencyViewAdapter
import com.example.currencyapp.presentation.adapters.HistoryViewAdapter
import com.example.currencyapp.presentation.viewmodel.DetailViewModel
import com.example.currencyapp.utils.IConstants
import dagger.hilt.android.AndroidEntryPoint


/**
 * Class represents the currency details screen
 * @author Sahil Salunke
 * @since 22/3/2022
 */
@AndroidEntryPoint
class DetailsFragment : Fragment() {

    // Binder instance for home fragment
    private lateinit var binding: FragmentDetailsBinding

    // ViewModel instance
    private val viewModel: DetailViewModel by viewModels()

    private var symbols: String = ""
    private var ratesList: ArrayList<Rates> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        symbols = arguments?.getString(IConstants.SYMBOLS).toString()
        ratesList = arguments?.getParcelableArrayList<Rates>(IConstants.RATES)!!
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getHistoryData(symbols)

        val currencyViewAdapter = CurrencyViewAdapter(ratesList)
        binding.topCurrencyAdapter = currencyViewAdapter

        viewModel.currencyHistory.observe(viewLifecycleOwner) {
            val historyAdapter = viewModel.currencyHistory.value?.let { HistoryViewAdapter(it) }
            binding.historyAdapter = historyAdapter
        }
    }

}