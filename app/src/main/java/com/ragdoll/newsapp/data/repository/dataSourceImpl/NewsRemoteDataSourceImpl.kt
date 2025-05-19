package com.ragdoll.newsapp.data.repository.dataSourceImpl

import com.ragdoll.newsapp.data.api.NewsAPIService
import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

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
    ): Response<APIResponse> = newsAPIService.getTopHeadlines(countryCode, category, page)

}