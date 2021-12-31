package com.br.guilherme.data.events.remote.model

import com.br.guilherme.domain.model.EventModel

data class Event  (
    override val people: List<Any?>,
    override val date: Long,
    override val description: String,
    override val image: String,
    override val longitude: Double,
    override val latitude: Double,
    override val price: Double,
    override val title: String,
    override val id: String
): EventModel