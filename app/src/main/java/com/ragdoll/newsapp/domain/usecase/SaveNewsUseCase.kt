package com.ragdoll.newsapp.domain.usecase

import com.ragdoll.newsapp.data.model.Article
import com.ragdoll.newsapp.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    /**
     * This function is used to save a news article to the local database.
     * @param article The article to be saved.
     */
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}