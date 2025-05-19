package com.ragdoll.newsapp.data.repository.dataSourceImpl

import com.ragdoll.newsapp.data.api.NewsAPIService
import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService,
    private val countryCode: String,
    private val category: String,
    private val page: Int,
) : NewsRemoteDataSource {
    /**
     * Fetches the top headlines from the News API.
     *
     * @return A [Response] containing the [APIResponse] with the top headlines.
     */
    override suspend fun getTopHeadlines(): Response<APIResponse> =
        newsAPIService.getTopHeadlines(countryCode, category, page)

}