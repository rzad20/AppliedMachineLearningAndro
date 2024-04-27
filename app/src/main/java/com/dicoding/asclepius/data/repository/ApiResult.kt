package com.dicoding.asclepius.data.repository

sealed class ApiResult<out R> private constructor(){
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val error: String) : ApiResult<Nothing>()
    object Loading: ApiResult<Nothing>()
    object Empty : ApiResult<Nothing>()
}