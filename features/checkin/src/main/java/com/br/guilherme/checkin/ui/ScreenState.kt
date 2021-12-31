package com.br.guilherme.checkin.ui

sealed class ScreenState {
    object Loading : ScreenState()
    object Success: ScreenState()
    data class Error(val t: Throwable) : ScreenState()
}