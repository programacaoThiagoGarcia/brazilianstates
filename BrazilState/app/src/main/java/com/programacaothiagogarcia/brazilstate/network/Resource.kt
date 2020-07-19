package com.programacaothiagogarcia.brazilstate.network


sealed class Resource<out T> {
    data class Success<out T>(val data : T?) : Resource<T>()
    data class Error<out T>(val msg : String, val data : T? =null) : Resource<T>()
}