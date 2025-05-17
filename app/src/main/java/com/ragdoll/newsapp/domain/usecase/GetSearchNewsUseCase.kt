package com.ragdoll.newsapp.domain.usecase

import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.domain.repository.NewsRepository

class GetSearchNewsUseCase(val newsRepository: NewsRepository) {
    /**
     * This function fetches news articles based on a search query from the repository.
     * It returns a Resource object containing the API response.
     *
     * @param searchQuery The search query to filter news articles.
     * @return A Resource object containing the API response.
     */
    suspend fun execute(searchQuery: String): Resource<APIResponse> =
        newsRepository.getSearchNews(searchQuery)
}