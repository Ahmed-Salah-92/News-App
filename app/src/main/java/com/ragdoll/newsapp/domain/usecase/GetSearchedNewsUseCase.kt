package com.ragdoll.newsapp.domain.usecase

import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.domain.repository.NewsRepository

class GetSearchedNewsUseCase(val newsRepository: NewsRepository) {
    /**
     * This function fetches news articles based on a search query from the repository.
     *
     * @param countryCode The country code to filter news articles.
     * @param category The category of news articles to fetch.
     * @param searchQuery The search query to filter news articles.
     * @param page The page number for pagination.
     * @return A Resource object containing the API response.
     */
    suspend fun execute(
        countryCode: String,
        category: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse> =
        newsRepository.getSearchedNews(countryCode, category, searchQuery, page)
}