package com.dicoding.asclepius.data.remote.response

data class ArticleResponse<out T>(
	val totalResults: Int,
	val articles: T,
	val status: String? = null
)