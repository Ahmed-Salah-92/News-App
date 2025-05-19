package com.ragdoll.newsapp.data.repository

import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.model.Article
import com.ragdoll.newsapp.data.repository.dataSource.NewsRemoteDataSource
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
) : NewsRepository {

    private fun responseToResource(response: Response<APIResponse>): Resource<APIResponse> {
        if (response.isSuccessful) {
            response.body()?.let { apiResponseResult ->
                return Resource.Success(apiResponseResult)
            }
        }
        return Resource.Error(response.message())
    }

    override suspend fun getNewsHeadlines(): Resource<APIResponse> =
        responseToResource(newsRemoteDataSource.getTopHeadlines())

    override suspend fun getNewsByCategory(category: String): Resource<APIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getSearchNews(searchQuery: String): Resource<APIResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun saveNews(article: Article) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNews(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }
    // Add any other methods or properties you need for your repository implementation
}