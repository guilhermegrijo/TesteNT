package com.br.guilherme.core.base

import androidx.annotation.Keep

@Keep
sealed class DataResult<out T> {
    data class OnSuccess<out T>(val response: T) : DataResult<T>()
    data class OnFailed(val throwable: Throwable) : DataResult<Nothing>()
}