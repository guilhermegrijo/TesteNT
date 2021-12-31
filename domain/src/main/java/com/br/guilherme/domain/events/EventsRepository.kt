package com.br.guilherme.domain.events

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.domain.model.EventModel
import kotlinx.coroutines.flow.Flow

interface EventsRepository {

    suspend fun getEvents() : Flow<DataResult<List<EventModel>>>
}