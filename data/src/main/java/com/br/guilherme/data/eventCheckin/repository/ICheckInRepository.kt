package com.br.guilherme.data.eventCheckin.repository

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.core.base.networkCall
import com.br.guilherme.core.di.NetworkModule
import com.br.guilherme.data.eventCheckin.remote.datasource.CheckInService
import com.br.guilherme.domain.checkin.usecase.CheckInRepository
import com.br.guilherme.domain.model.CheckInModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class ICheckInRepository @Inject constructor(@NetworkModule.ApiEvent private val remoteDataSource : CheckInService) : CheckInRepository{
    override suspend fun makeCheckin(request: CheckInModel): Flow<DataResult<Unit>> {
        return flow {
            networkCall { remoteDataSource.makeCheckIn(request) }
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