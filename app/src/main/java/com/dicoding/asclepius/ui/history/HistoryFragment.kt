package com.dicoding.asclepius.ui.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.base.BaseFragment
import com.dicoding.asclepius.data.local.entity.AnalyzeHistory
import com.dicoding.asclepius.databinding.FragmentHistoryBinding
import com.dicoding.asclepius.ui.adapters.HistoryAdapter
import com.dicoding.asclepius.viewModelFactory.HistoryModelFactory

class HistoryFragment : BaseFragment<FragmentHistoryBinding>() {
    private val historyViewModel by viewModels<HistoryViewModel> {
        HistoryModelFactory.getInstance(requireActivity())
    }
    private lateinit var historyAdapter : HistoryAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentHistoryBinding {
        return FragmentHistoryBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        historyAdapter = HistoryAdapter()
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = historyAdapter

        }
    }

    override fun initProcess() {
    }

    override fun initObservers() {
        historyViewModel.getAnalyzeHistory().observe(this) {analyzeHistory ->
            val items = arrayListOf<AnalyzeHistory>()
            analyzeHistory.map {
                val item = AnalyzeHistory(id = it.id, predictedLabel = it.predictedLabel, confidenceScore = it.confidenceScore, imageUri = it.imageUri)
                items.add(item)
            }
            historyAdapter.submitList(items)
        }
    }

}