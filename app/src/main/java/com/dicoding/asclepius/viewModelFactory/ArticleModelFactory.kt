package com.dicoding.asclepius.viewModelFactory

import com.dicoding.asclepius.data.repository.ArticlesRepository
import com.dicoding.asclepius.ui.article.ArticleViewModel
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.di.remoteInjection

class ArticleModelFactory private constructor(
    private var articlesRepository: ArticlesRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(articlesRepository) as T
        }
        throw IllegalArgumentException("Uknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: ArticleModelFactory? = null
        fun getInstance(context: Context) : ArticleModelFactory =
            instance ?: synchronized(this) {
                instance ?: ArticleModelFactory(remoteInjection.provideNetworkRepository(context))
            }
    }
}