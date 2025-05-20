package com.ragdoll.newsapp.presentation.di

import android.app.Application
import com.ragdoll.newsapp.domain.usecase.GetNewsHeadLinesUseCase
import com.ragdoll.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadLinesUseCase: GetNewsHeadLinesUseCase
    ): NewsViewModelFactory = NewsViewModelFactory(application, getNewsHeadLinesUseCase)

}