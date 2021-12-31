package com.br.guilherme.data.eventDetail.remote.datasource

import com.br.guilherme.data.eventDetail.remote.model.EventDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface EventDetailService {

    @GET("/api/events/{id}")
    suspend fun getEventDetail(@Path("id") id: String): Response<EventDetail>

}