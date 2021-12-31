package com.br.guilherme.core.base

import kotlinx.coroutines.flow.Flow

interface UseCase<in I, out R : Any> {
    suspend fun invoke(input: I): Flow<DataResult<R>>
}