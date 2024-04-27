package com.dicoding.asclepius.ui.history

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.repository.HistoryRepository

class HistoryViewModel (private val historyRepository: HistoryRepository) : ViewModel() {
    fun getAnalyzeHistory() = historyRepository.getAnalyzeHistory()
}