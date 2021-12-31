package com.br.guilherme.data.eventCheckin.di

import com.br.guilherme.core.di.NetworkModule
import com.br.guilherme.data.eventCheckin.remote.datasource.CheckInService
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
    fun provideEventDetailService(@NetworkModule.ApiEvent retrofit: Retrofit) : CheckInService {
        return retrofit.create(CheckInService::class.java)
    }
}