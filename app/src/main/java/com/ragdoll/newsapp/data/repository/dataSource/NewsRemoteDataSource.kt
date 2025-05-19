package com.ragdoll.newsapp.data.repository.dataSource

import com.ragdoll.newsapp.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(): Response<APIResponse>
}