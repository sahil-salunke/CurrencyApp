package com.example.currencyapp.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.example.currencyapp.R
import com.example.currencyapp.data.containers.CurrencyHistoryDTO
import com.example.currencyapp.databinding.HistoryCurrenciesBinding

/**
 * A recyclerview adapter to show history currencies
 * @author Sahil Salunke
 * @since 27/3/2022
 */
class HistoryViewAdapter(private val currencyHistory: List<CurrencyHistoryDTO>) :
    RecyclerView.Adapter<HistoryViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: HistoryCurrenciesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.history_currencies, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currencyHistory: CurrencyHistoryDTO = currencyHistory[position]
        holder.bind(currencyHistory)
    }

    override fun getItemCount(): Int {
        return currencyHistory.size
    }

    inner class ViewHolder(itemRowBinding: HistoryCurrenciesBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        private var topTenCurrenciesBinding: HistoryCurrenciesBinding = itemRowBinding
        fun bind(obj: CurrencyHistoryDTO?) {
            topTenCurrenciesBinding.history = obj
            topTenCurrenciesBinding.executePendingBindings()
        }
    }
}