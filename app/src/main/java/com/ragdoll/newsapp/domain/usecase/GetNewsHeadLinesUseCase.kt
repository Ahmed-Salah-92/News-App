package com.ragdoll.newsapp.domain.usecase

import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.domain.repository.NewsRepository

class GetNewsHeadLinesUseCase(private val newsRepository: NewsRepository) {
    /**
     * This function fetches news articles from the repository.
     * It returns a Resource object containing the API response.
     *
     * @return A Resource object containing the API response.
     */
    suspend fun execute(countryCode: String, category: String, page: Int): Resource<APIResponse> =
        newsRepository.getNewsHeadlines(countryCode, category, page)
}