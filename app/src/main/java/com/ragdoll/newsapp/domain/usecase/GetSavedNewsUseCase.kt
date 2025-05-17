package com.ragdoll.newsapp.domain.usecase

import com.ragdoll.newsapp.data.model.Article
import com.ragdoll.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
    /**
     * This use case retrieves the list of saved news articles from the repository.
     * It returns a Flow that emits a list of Article objects.
     */
    fun execute(): Flow<List<Article>> = newsRepository.getSavedNews()
}