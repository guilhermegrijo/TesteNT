package com.br.guilherme.data.eventDetail.remote.model

import com.br.guilherme.domain.model.EventModel

data class EventDetail(
    override val people: List<Any?>,
    override val date: Long,
    override val description: String,
    override val image: String,
    override val longitude: Double,
    override val latitude: Double,
    override val price: Double,
    override val title: String,
    override val id: String
) : EventModel
