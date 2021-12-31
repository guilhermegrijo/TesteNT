package com.br.guilherme.data.eventDetail.repository

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.core.base.networkCall
import com.br.guilherme.core.di.NetworkModule
import com.br.guilherme.data.eventDetail.remote.datasource.EventDetailService
import com.br.guilherme.domain.eventDetail.EventDetailRepository
import com.br.guilherme.domain.model.EventModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class IEventDetailRepository @Inject constructor(@NetworkModule.ApiEvent private val remoteDataSource : EventDetailService): EventDetailRepository {

    override suspend fun getEventDetail(id: String): Flow<DataResult<EventModel>> {

        return flow {
            networkCall { remoteDataSource.getEventDetail(id) }
                .catch { e -> emit(DataResult.OnFailed(IOException("${e.message}"))) }
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
}