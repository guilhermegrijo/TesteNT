package com.br.guilherme.data.events.remote.datasource

import com.br.guilherme.data.events.remote.model.Event
import retrofit2.Response
import retrofit2.http.GET

interface EventService {

    @GET("/api/events")
    suspend fun getEvents() : Response<List<Event>>
}