package com.example.currencyapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.currencyapp.databinding.FragmentHomeBinding
import com.example.currencyapp.viewmodel.HomeViewModel
import com.example.currencyapp.viewmodelfactory.HomeViewModelFactory

/**
 * Class represents the currency conversion screen
 * @author Sahil Salunke
 * @since 15/3/2022
 */
class HomeFragment : Fragment() {

    // Binder instance for home fragment
    private lateinit var binding: FragmentHomeBinding

    // ViewModel instance
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
    }

}