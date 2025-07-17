package com.ragdoll.newsapp.presentation.di

import com.ragdoll.newsapp.data.api.NewsAPIService
import com.ragdoll.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.ragdoll.newsapp.data.repository.dataSourceImpl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsAPIService: NewsAPIService): NewsRemoteDataSource =
        NewsRemoteDataSourceImpl(newsAPIService)

}