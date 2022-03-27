package com.example.currencyapp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.currencyapp.R
import com.example.currencyapp.data.containers.Rates
import com.example.currencyapp.databinding.TopTenCurrenciesBinding

/**
 * A recyclerview adapter to show top ten currencies
 * @author Sahil Salunke
 * @since 27/3/2022
 */
class CurrencyViewAdapter(private val rates: ArrayList<Rates>) :
    RecyclerView.Adapter<CurrencyViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: TopTenCurrenciesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.top_ten_currencies, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rate: Rates = rates[position]
        holder.bind(rate)
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    inner class ViewHolder(itemRowBinding: TopTenCurrenciesBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        private var topTenCurrenciesBinding: TopTenCurrenciesBinding = itemRowBinding
        fun bind(obj: Rates?) {
            topTenCurrenciesBinding.rates = obj
            topTenCurrenciesBinding.executePendingBindings()
        }
    }
}