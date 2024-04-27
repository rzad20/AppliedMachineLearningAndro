package com.dicoding.asclepius.ui.article

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.R
import com.dicoding.asclepius.base.BaseFragment
import com.dicoding.asclepius.data.repository.ApiResult
import com.dicoding.asclepius.databinding.FragmentArticleBinding
import com.dicoding.asclepius.ui.adapters.ArticleAdapter
import com.dicoding.asclepius.ui.adapters.HistoryAdapter
import com.dicoding.asclepius.viewModelFactory.ArticleModelFactory

class ArticleFragment : BaseFragment<FragmentArticleBinding>() {
    private val articleViewModel by viewModels<ArticleViewModel> {
        ArticleModelFactory.getInstance(requireContext())
    }
    private lateinit var articleAdapter : ArticleAdapter
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentArticleBinding {
        return FragmentArticleBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        articleAdapter = ArticleAdapter()
        binding.rvArticle.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = articleAdapter

        }
    }

    override fun initProcess() {
        articleViewModel.fetchArticles()
    }

    override fun initObservers() {
        articleViewModel.articleList.observe(this) {result ->
            if(result != null) {
                when(result) {
                    is ApiResult.Loading -> {
                        showLoading(true)
                    }
                    is ApiResult.Success -> {
                        showLoading(false)
                        val data = result.data
                        articleAdapter.setData(data)
                    }
                    is ApiResult.Error -> {
                        showLoading(false)
                        Log.e("ArticleViewModel", "Error : ${result.error}")
                    }
                    is ApiResult.Empty -> {
                        showLoading(false)
                        Log.e("ArticleViewModel", "Data Empty")
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}