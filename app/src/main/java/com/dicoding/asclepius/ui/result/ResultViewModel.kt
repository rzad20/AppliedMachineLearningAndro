package com.dicoding.asclepius.ui.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.asclepius.data.local.entity.AnalyzeHistory
import com.dicoding.asclepius.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class ResultViewModel (private val historyRepository: HistoryRepository) : ViewModel() {
    fun addHistory(predictedLabel: String, confidence: String, imageFile: String) {
        viewModelScope.launch {
            val history =
                AnalyzeHistory (
                    id = 0,
                    predictedLabel = predictedLabel,
                    confidenceScore = confidence,
                    imageUri = imageFile
                )
            historyRepository.setAnalyzeHistory(history)
        }
    }
}