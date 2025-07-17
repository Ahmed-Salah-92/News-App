package com.ragdoll.newsapp.domain.repository

import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.model.Article
import com.ragdoll.newsapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Interface representing the repository for news articles.
 * It provides methods to fetch, save, and delete news articles.
 */
interface NewsRepository {
    /**
     * Fetches news headlines from the API based on the provided country code, category, and page number.
     *
     * @param countryCode The country code to filter news articles.
     * @param category The category of news articles to fetch.
     * @param page The page number for pagination.
     * @return A Resource object containing the API response with news articles.
     */
    suspend fun getNewsHeadlines(
        countryCode: String,
        category: String,
        page: Int
    ): Resource<APIResponse>

    // Fetching news articles by category from the API
    suspend fun getNewsByCategory(category: String): Resource<APIResponse>
    /**
     * Fetching news articles based on a search query from the API
     *
     * @param countryCode The country code to filter news articles.
     * @param category The category of news articles to fetch.
     * @param searchQuery The search query to filter news articles.
     * @param page The page number for pagination.
     * @return A Resource object containing the API response with news articles.
    * */
    suspend fun getSearchedNews(
        countryCode: String,
        category: String,
        searchQuery: String,
        page: Int
    ): Resource<APIResponse>


    // Saving and deleting news articles from the local database
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    // Fetching saved news articles from the local database
    fun getSavedNews(): Flow<List<Article>>
}