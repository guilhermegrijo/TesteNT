package com.br.guilherme.data.eventDetail.di

import com.br.guilherme.core.di.NetworkModule
import com.br.guilherme.data.eventDetail.remote.datasource.EventDetailService
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
    fun provideEventDetailService(@NetworkModule.ApiEvent retrofit: Retrofit) : EventDetailService {
        return retrofit.create(EventDetailService::class.java)
    }
}