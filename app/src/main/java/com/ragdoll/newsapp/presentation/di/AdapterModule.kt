package com.ragdoll.newsapp.presentation.di

import com.ragdoll.newsapp.presentation.adapter.ArticleAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Singleton
    @Provides
    fun provideArticleAdapter(): ArticleAdapter = ArticleAdapter()
}