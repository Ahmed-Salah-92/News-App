package com.ragdoll.newsapp.presentation.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ragdoll.newsapp.domain.usecase.GetNewsHeadLinesUseCase
import com.ragdoll.newsapp.domain.usecase.GetSearchedNewsUseCase

/**
 * This class is a factory for creating instances of the NewsViewModel.
 * It takes an Application and a GetNewsHeadLinesUseCase as parameters.
 *
 * @param app The application instance.
 * @param getNewsHeadLinesUseCase The use case for fetching news headlines.
 */

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadLinesUseCase: GetNewsHeadLinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val connectivityManager: ConnectivityManager,
) : ViewModelProvider.Factory {
    /**
     * This function creates a ViewModel instance of the specified class.
     * It is used to create the ViewModel for the NewsFragment.
     *
     * @param modelClass The class of the ViewModel to be created.
     * @return An instance of the specified ViewModel class.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(
            app,
            getNewsHeadLinesUseCase,
            getSearchedNewsUseCase,
            connectivityManager
        ) as T
    }
}