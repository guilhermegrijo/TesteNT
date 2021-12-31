package com.br.guilherme.data.events.di

import com.br.guilherme.data.events.repository.IEventsRepository
import com.br.guilherme.domain.events.EventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideEventsRepository(repository: IEventsRepository) : EventsRepository

}