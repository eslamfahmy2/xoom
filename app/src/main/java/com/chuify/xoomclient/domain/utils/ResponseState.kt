package com.chuify.xoomclient.domain.utils


sealed class ResponseState<out T> {
    data class Success<T>(val data: T) : ResponseState<T>()
    data class Error(val message: String? = null) : ResponseState<Nothing>()
}

