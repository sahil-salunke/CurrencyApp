package com.example.currencyapp.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.currencyapp.databinding.FragmentDetailsBinding
import com.example.currencyapp.presentation.viewmodel.HomeViewModel


/**
 * Class represents the currency details screen
 * @author Sahil Salunke
 * @since 22/3/2022
 */
class DetailsFragment : Fragment() {

    // Binder instance for home fragment
    private lateinit var binding: FragmentDetailsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

}