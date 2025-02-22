package com.example.my_shoppings.util

sealed class Response<T>(val data: T? = null,
    val message: String? = null) {
    class Success<T>(data: T) : Response<T>(data)
    class Error<T>(message: String) : Response<T>( null,message)
    class Loading<T> : Response<T>()
}
