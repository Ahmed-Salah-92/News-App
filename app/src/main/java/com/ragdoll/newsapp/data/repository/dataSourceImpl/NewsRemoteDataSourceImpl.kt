package com.ragdoll.newsapp.data.repository.dataSourceImpl

import com.ragdoll.newsapp.data.api.NewsAPIService
import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

/**
 * Implementation of the [NewsRemoteDataSource] interface that interacts with the News API.
 *
 * @property newsAPIService The service used to make network requests to the News API.
 */
class NewsRemoteDataSourceImpl(private val newsAPIService: NewsAPIService) : NewsRemoteDataSource {
    /**
     * Fetches the top headlines from the News API.
     *
     * @return A [Response] containing the [APIResponse] with the top headlines.
     */
    override suspend fun getTopHeadlines(
        countryCode: String,
        category: String,
        page: Int
    ): Response<APIResponse> =
        newsAPIService.getTopHeadlines(countryCode, category, page)

    /**
     * Fetches news articles based on a search query from the News API.
     *
     * @return A [Response] containing the [APIResponse] with the searched news articles.
     */
    override suspend fun getSearchedNews(
        countryCode: String,
        category: String,
        searchQuery: String,
        page: Int
    ): Response<APIResponse> =
        newsAPIService.getSearchedNews(countryCode, category, searchQuery, page)

}