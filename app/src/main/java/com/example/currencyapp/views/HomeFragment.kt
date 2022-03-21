package com.example.currencyapp.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.currencyapp.databinding.FragmentHomeBinding
import com.example.currencyapp.network.RetrofitBuilder
import com.example.currencyapp.network.Status
import com.example.currencyapp.repository.HomeRepository
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
        setupObservers()
    }

    private fun initializeViewModel() {

        val homeRepository = HomeRepository(RetrofitBuilder.currencyService)

        viewModel = ViewModelProviders.of(
            this,
            HomeViewModelFactory(homeRepository)
        ).get(HomeViewModel::class.java)

//        viewModel = ViewModelProvider(
//            this,
//            HomeViewModelFactory(HomeRepository(RetrofitBuilder.currencyService))
//        ).get(HomeViewModel::class.java)
    }

    private fun setupObservers() {
        viewModel.getCurrencySymbols().observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
//                        resource.data?.let { users -> retrieveList(users) }
                       Toast.makeText(activity, "SUCCESS", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
//                        recyclerView.visibility = View.VISIBLE
//                        progressBar.visibility = View.GONE
                       Toast.makeText(activity, "ERROR", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
//                        progressBar.visibility = View.VISIBLE
//                        recyclerView.visibility = View.GONE
                       Toast.makeText(activity, "LOADING", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }


}