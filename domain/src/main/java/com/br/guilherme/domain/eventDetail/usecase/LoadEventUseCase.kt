package com.br.guilherme.domain.eventDetail.usecase

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.core.base.UseCase
import com.br.guilherme.domain.eventDetail.EventDetailRepository
import com.br.guilherme.domain.model.EventModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadEventUseCase @Inject constructor(private val eventDetailRepository: EventDetailRepository) :
    UseCase<String, EventModel> {
    override suspend fun invoke(input: String): Flow<DataResult<EventModel>> {
        return eventDetailRepository.getEventDetail(input)
    }
}