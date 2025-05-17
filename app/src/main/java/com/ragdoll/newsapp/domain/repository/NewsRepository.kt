package com.ragdoll.newsapp.domain.repository

import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.model.Article
import com.ragdoll.newsapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    // Fetching news articles from the API
    suspend fun getNewsHeadlines(): Resource<APIResponse>
    suspend fun getNewsByCategory(category: String): Resource<APIResponse>
    suspend fun getSearchNews(searchQuery: String): Resource<APIResponse>

    // Saving and deleting news articles from the local database
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)

    // Fetching saved news articles from the local database
    fun getSavedNews(): Flow<List<Article>>
}