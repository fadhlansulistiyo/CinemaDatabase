package com.fadhlansulistiyo.cinemadatabase.core.data.remote.network

sealed class ApiResponseResult<out R> {
    data class Success<out T>(val data: T) : com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult<T>()
    data class Error(val errorMessage: String) : com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult<Nothing>()
    object Empty : com.fadhlansulistiyo.cinemadatabase.core.data.remote.network.ApiResponseResult<Nothing>()
}