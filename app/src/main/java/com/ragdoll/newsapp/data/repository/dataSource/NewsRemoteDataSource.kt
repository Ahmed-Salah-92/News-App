package com.ragdoll.newsapp.data.repository.dataSource

import com.ragdoll.newsapp.data.model.APIResponse
import retrofit2.Response

// Interface for the remote data source to fetch news articles from the News API
interface NewsRemoteDataSource {
    // Fetch top headlines from the News API
    suspend fun getTopHeadlines(
        countryCode: String,
        category: String,
        page: Int
    ): Response<APIResponse>

    // Fetch news articles based on a search query
    suspend fun getSearchedNews(
        countryCode: String,
        category: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse>
}