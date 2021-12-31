package com.br.guilherme.data.eventDetail.di

import com.br.guilherme.data.eventDetail.repository.IEventDetailRepository
import com.br.guilherme.data.events.repository.IEventsRepository
import com.br.guilherme.domain.eventDetail.EventDetailRepository
import com.br.guilherme.domain.events.EventsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideEventDetailRepository(repository: IEventDetailRepository) : EventDetailRepository

}