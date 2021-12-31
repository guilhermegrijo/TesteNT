package com.br.guilherme.data.events.repository

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.core.base.networkCall
import com.br.guilherme.core.di.NetworkModule
import com.br.guilherme.data.events.remote.datasource.EventService
import com.br.guilherme.domain.events.EventsRepository
import com.br.guilherme.domain.model.EventModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IEventsRepository @Inject constructor(@NetworkModule.ApiEvent private val remoteDataSource: EventService) :
    EventsRepository {
    override suspend fun getEvents(): Flow<DataResult<List<EventModel>>> = flow {


        networkCall { remoteDataSource.getEvents() }
            .catch { e -> emit(DataResult.OnFailed(java.io.IOException("${e.message}"))) }
            .collect {
                when (it) {
                    is DataResult.OnSuccess -> emit(it)
                    is DataResult.OnFailed -> emit(DataResult.OnFailed(it.throwable))
                    else -> {
                        emit(DataResult.OnFailed(Exception("Error")))
                    }
                }
            }
    }

}