package com.br.guilherme.domain.events.usecase

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.core.base.UseCase
import com.br.guilherme.domain.events.EventsRepository
import com.br.guilherme.domain.model.EventModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadAllEventsUseCase @Inject constructor(private val eventsRepository: EventsRepository) :
    UseCase<Unit, List<EventModel>> {
    override suspend fun invoke(input: Unit): Flow<DataResult<List<EventModel>>> {
        return eventsRepository.getEvents()
    }

}