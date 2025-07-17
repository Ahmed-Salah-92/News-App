package com.ragdoll.newsapp.presentation.di

import com.ragdoll.newsapp.domain.repository.NewsRepository
import com.ragdoll.newsapp.domain.usecase.GetNewsHeadLinesUseCase
import com.ragdoll.newsapp.domain.usecase.GetSearchedNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetNewsHeadLineUseCase(newsRepository: NewsRepository): GetNewsHeadLinesUseCase =
        GetNewsHeadLinesUseCase(newsRepository)

    @Singleton
    @Provides
    fun provideGetSearchedNewsUseCase(newsRepository: NewsRepository): GetSearchedNewsUseCase =
        GetSearchedNewsUseCase(newsRepository)


}