package com.example.currencyapp.presentation.views

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.currencyapp.R
import com.example.currencyapp.data.containers.Rates
import com.example.currencyapp.databinding.FragmentHomeBinding
import com.example.currencyapp.presentation.viewmodel.HomeViewModel
import com.example.currencyapp.utils.EventListener
import com.example.currencyapp.utils.IConstants
import com.example.currencyapp.utils.Utils
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
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getCurrencyData()
        binding.progress.visibility = View.VISIBLE
    }

    /**
     * Manages the keyboard go button click events
     */
    override fun onGoButtonClick(view: View, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            when (view.id) {
                binding.etAmountFrom.id -> {
                    val fromValue = binding.etAmountFrom.text.toString().toDoubleOrNull()
                    viewModel.onToInputValue(fromValue)
                }
                binding.etAmountTo.id -> {
                    val fromValue = binding.etAmountTo.text.toString().toDoubleOrNull()
                    viewModel.onFromInputValue(fromValue)
                }
            }
        }
        return true
    }

    /**
     * Manages the spinner item click events
     */
    override fun onSelectItem(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        binding.progress.visibility = View.GONE
        when (parent?.id) {
            binding.spFrom.id -> {
                viewModel.onFromSelectedItem(binding.spFrom.selectedItem.toString())
            }
            binding.spTo.id -> {
                viewModel.onToSelectedItem(binding.spTo.selectedItem.toString())
            }
        }
    }

    /**
     * Manages the button click events
     */
    override fun onButtonClick(view: View) {
        when (view.id) {
            binding.ivCenter.id -> {
                swapSelectedCurrencies()
            }
            binding.btDetails.id -> {
                moveToDetailsScreenWithData()
            }
        }
    }

    /**
     * Swap selected currencies
     */
    private fun swapSelectedCurrencies() {
        Utils.swapSelectedCurrencies(binding.spFrom, binding.spTo)
        setInitialState()
        viewModel.onFromSelectedItem(binding.spFrom.selectedItem.toString())
        viewModel.onToSelectedItem(binding.spTo.selectedItem.toString())
        val fromValue = binding.etAmountFrom.text.toString().toDoubleOrNull()
        viewModel.onToInputValue(fromValue)
    }

    /**
     * Collect data and move to details screen
     */
    private fun moveToDetailsScreenWithData() {
        val arrayList: ArrayList<Rates> = Utils.linkedHashMapToArrayList(viewModel.rates)
        val bundle = bundleOf(
            IConstants.SYMBOLS to binding.spFrom.selectedItem.toString() + "," +
                    binding.spTo.selectedItem.toString(),
            IConstants.RATES to arrayList
        )
        findNavController().navigate(R.id.action_home_to_details, bundle)
    }

    /**
     * Reset input fields
     */
    private fun setInitialState() {
        binding.etAmountFrom.setText(resources.getString(R.string.initialVal))
        binding.etAmountTo.setText("")
    }

}