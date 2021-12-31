package com.br.guilherme.domain.checkin

import com.br.guilherme.core.base.DataResult
import com.br.guilherme.core.base.UseCase
import com.br.guilherme.domain.checkin.usecase.CheckInRepository
import com.br.guilherme.domain.model.CheckInModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MakeCheckInUseCase @Inject constructor(private val checkInRepository: CheckInRepository): UseCase<CheckInModel, Unit> {
    override suspend fun invoke(input: CheckInModel): Flow<DataResult<Unit>> {
        return checkInRepository.makeCheckin(input)
    }
}