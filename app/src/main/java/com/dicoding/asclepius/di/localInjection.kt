package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.room.AnalyzeHistoryDatabase
import com.dicoding.asclepius.data.repository.HistoryRepository

object localInjection {
    fun providelocalRepository (context: Context) : HistoryRepository {
        val database = AnalyzeHistoryDatabase.getInstance(context)
        val dao = database.AnalyzeDao()
        return HistoryRepository.getInstance(dao)
    }
}