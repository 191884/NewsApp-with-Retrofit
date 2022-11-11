package com.yo.newsapp.utils

sealed class Resource<T>(
    val data : T? = null,
    val message : String? = null
){
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(Message: String, data: T? = null) : Resource<T>(data, Message)
    class Loading<T> : Resource<T>()
}
