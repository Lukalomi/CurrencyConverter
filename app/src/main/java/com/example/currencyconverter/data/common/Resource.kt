package com.example.currencyconverter.data.common

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val errorMessage: String) : Resource<T>()
    data class Loader<T>(val isLoading: Boolean) : Resource<T>()

    data class SuccessEvent<T>(var resultText: T) : Resource<T>()
    data class FailureEvent<T>(val errorText: String) : Resource<T>()
    data class LoaderEvent<T>(val loading: Boolean) : Resource<T>()

    data class Empty<T>(val empty: String) : Resource<T>()
}