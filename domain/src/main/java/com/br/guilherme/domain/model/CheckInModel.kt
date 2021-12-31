package com.br.guilherme.domain.model

import com.google.gson.annotations.SerializedName

data class CheckInModel (
    @SerializedName("EventId")
    val eventID: String,
    val name: String,
    val email: String
)