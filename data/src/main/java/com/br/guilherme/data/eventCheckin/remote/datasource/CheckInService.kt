package com.br.guilherme.data.eventCheckin.remote.datasource

import com.br.guilherme.domain.model.CheckInModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckInService {

    @POST("/api/checkin")
    suspend fun makeCheckIn(@Body request: CheckInModel) : Response<Unit>

}