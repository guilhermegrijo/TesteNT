package com.br.guilherme.testent.di

import com.br.guilherme.core.di.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @NetworkModule.ApiEvent
    fun provideApiUrl(): String = "http://5f5a8f24d44d640016169133.mockapi.io/"


}