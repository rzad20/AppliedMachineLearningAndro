package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.data.repository.ArticlesRepository

object remoteInjection {
    fun provideNetworkRepository (context: Context) : ArticlesRepository {
        val apiService = ApiConfig.getApiService()
        return ArticlesRepository.getInstance(apiService)
    }
}