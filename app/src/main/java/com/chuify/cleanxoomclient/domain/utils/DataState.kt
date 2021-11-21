package com.chuify.cleanxoomclient.domain.utils

sealed class DataState<out T> {
    data class Success<T>(val data: T? = null) : DataState<T>()
    data class Error(val message: String? = null) : DataState<Nothing>()
    data class Loading(val message: String? = null) : DataState<Nothing>()
}