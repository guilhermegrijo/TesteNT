package com.br.guilherme.domain.checkin.usecase

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.domain.model.CheckInModel
import kotlinx.coroutines.flow.Flow

interface CheckInRepository {

    suspend fun makeCheckin(request : CheckInModel) : Flow<DataResult<Unit>>
}