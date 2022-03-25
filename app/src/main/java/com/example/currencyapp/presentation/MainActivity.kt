package com.example.currencyapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.currencyapp.R
import com.example.currencyapp.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity class for entire application
 * @author Sahil Salunke
 * @since 15/3/2022
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}