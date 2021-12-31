package com.br.guilherme.data.events.di

import com.br.guilherme.core.di.NetworkModule
import com.br.guilherme.data.events.remote.datasource.EventService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class ServiceModule {

    @Provides
    @NetworkModule.ApiEvent
    fun provideEventService(@NetworkModule.ApiEvent retrofit: Retrofit) : EventService {
        return retrofit.create(EventService::class.java)
    }

}