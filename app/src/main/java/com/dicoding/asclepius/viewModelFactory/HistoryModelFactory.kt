package com.dicoding.asclepius.viewModelFactory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.HistoryRepository
import com.dicoding.asclepius.di.localInjection
import com.dicoding.asclepius.ui.history.HistoryViewModel
import com.dicoding.asclepius.ui.result.ResultViewModel

class HistoryModelFactory private constructor(
    private var historyRepository: HistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(historyRepository) as T
        } else if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(historyRepository) as T
        }
        throw IllegalArgumentException("Uknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: HistoryModelFactory? = null
        fun getInstance(context: Context) : HistoryModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryModelFactory(localInjection.providelocalRepository(context))
            }
    }
}