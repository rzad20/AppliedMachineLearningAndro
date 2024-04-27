package com.dicoding.asclepius.data.repository

import androidx.lifecycle.LiveData
import com.dicoding.asclepius.data.local.entity.AnalyzeHistory
import com.dicoding.asclepius.data.local.room.AnalyzeDao

class HistoryRepository private constructor (
    private val historyDao: AnalyzeDao
) {
    fun getAnalyzeHistory() : LiveData<List<AnalyzeHistory>> {
        return historyDao.getAllHistories()
    }

    suspend fun setAnalyzeHistory(history: AnalyzeHistory) {
        historyDao.insertHistory(history)
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
            historyDao: AnalyzeDao
        ) : HistoryRepository =
            instance ?: synchronized(this) {
                instance ?: HistoryRepository(historyDao)
            }.also {instance = it}
    }

}