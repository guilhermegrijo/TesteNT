package com.br.guilherme.data.eventCheckin.di

import com.br.guilherme.data.eventCheckin.repository.ICheckInRepository
import com.br.guilherme.domain.checkin.usecase.CheckInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCheckInRepository(repository: ICheckInRepository) : CheckInRepository

}