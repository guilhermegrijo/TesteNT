package com.br.guilherme.domain.eventDetail

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.domain.model.EventModel
import kotlinx.coroutines.flow.Flow

interface EventDetailRepository {

    suspend fun getEventDetail(id : String) : Flow<DataResult<EventModel>>

}