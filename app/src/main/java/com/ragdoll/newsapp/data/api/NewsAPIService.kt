package com.ragdoll.newsapp.data.api

import com.ragdoll.newsapp.BuildConfig
import com.ragdoll.newsapp.data.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") countryCode: String,
        @Query("category") category: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY,
    ): Response<APIResponse>
}