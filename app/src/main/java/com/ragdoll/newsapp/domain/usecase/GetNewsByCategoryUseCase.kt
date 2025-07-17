package com.ragdoll.newsapp.domain.usecase

import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.domain.repository.NewsRepository

class GetNewsByCategoryUseCase(val repository: NewsRepository) {
    /**
     * This function fetches news articles by category from the repository.
     * It returns a Resource object containing the API response.
     *
     * @param category The category of news to fetch.
     * @return A Resource object containing the API response.
     */
    suspend fun execute(category: String): Resource<APIResponse> =
        repository.getNewsByCategory(category)
}