package com.ragdoll.newsapp.domain.usecase


import com.ragdoll.newsapp.data.model.Article
import com.ragdoll.newsapp.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
    /**
     * This function is used to delete a saved news article from the local database.
     * @param article The article to be deleted.
     */
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}