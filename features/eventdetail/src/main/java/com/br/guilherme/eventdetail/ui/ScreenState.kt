package com.br.guilherme.eventdetail.ui

sealed class ScreenState<out T> {
    object Loading : ScreenState<Nothing>()
    data class Success<T>(val data: T) : ScreenState<T>()
    data class Error(val t: Throwable) : ScreenState<Nothing>()

    fun toData(): T? = if(this is Success) this.data else null
}