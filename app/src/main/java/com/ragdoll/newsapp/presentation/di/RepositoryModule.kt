package com.ragdoll.newsapp.presentation.di

import com.ragdoll.newsapp.data.repository.NewsRepositoryImpl
import com.ragdoll.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.ragdoll.newsapp.domain.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(newsRemoteDataSource: NewsRemoteDataSource): NewsRepository =
        NewsRepositoryImpl(newsRemoteDataSource)

}