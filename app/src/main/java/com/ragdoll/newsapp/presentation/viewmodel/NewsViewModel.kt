package com.ragdoll.newsapp.presentation.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ragdoll.newsapp.R
import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.domain.usecase.GetNewsHeadLinesUseCase
import com.ragdoll.newsapp.domain.usecase.GetSearchedNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadLinesUseCase: GetNewsHeadLinesUseCase,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val connectivityManager: ConnectivityManager,        // Injected ConnectivityManager for network checks
) : AndroidViewModel(app) {

    // Function to check network availability using coroutines
    private suspend fun isNetworkAvailable(): Boolean {
        return withContext(Dispatchers.IO) {
            val network = connectivityManager.activeNetwork ?: return@withContext false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return@withContext false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
    }

    // LiveData to hold the news headlines
    private val _newsHeadLines = MutableLiveData<Resource<APIResponse>>()
    val newsHeadLines: LiveData<Resource<APIResponse>> get() = _newsHeadLines

    fun getNewsHeadLines(countryCode: String, category: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {

            _newsHeadLines.postValue(Resource.Loading) // Set loading state
            try {
                if (isNetworkAvailable()) { // Check if the network is available before making the API call
                    val apiResponse = getNewsHeadLinesUseCase.execute(countryCode, category, page)
                    _newsHeadLines.postValue(apiResponse)
                } else
                    _newsHeadLines.postValue(Resource.Error(app.getString(R.string.no_internet_connection)))
            } catch (e: Exception) {
                Log.e("NewsViewModel", "API error: ${e.message}",e)
                _newsHeadLines.postValue(Resource.Error(e.message.toString()))
            }
        }

    // Function to check network availability old approach
 /*   private fun isNetworkAvailable(app: Application): Boolean { // Implement network availability check

        val context = app.applicationContext // Get the application context
        if (context == null) return false // Check if the context is null

        // Network Capabilities Builder pattern
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        // Create a NetworkRequest object with the desired capabilities
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
            }

            // Network capabilities have changed for the network
            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                val unmetered =
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
            }
        }
        // Register the network callback with the ConnectivityManager
        val connectivityManager =
            context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
        // Check if the device is connected to a network
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            // Check if the capabilities are not null
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true // Wi-Fi is available
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true // Cellular data is available
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true // Ethernet is available
                }
            }
        }
        return false // No network available
    }*/

    fun refreshNewsHeadLines(countryCode: String, category: String, page: Int = 1) {
        getNewsHeadLines(countryCode, category, page)
    }

    // Function to search news articles based on a query
    private val _searchNews = MutableLiveData<Resource<APIResponse>>()
    val searchNews: LiveData<Resource<APIResponse>> get() = _searchNews

    fun searchedNews(countryCode: String, category: String, searchQuery: String, page: Int) =
        viewModelScope.launch {

            _searchNews.postValue(Resource.Loading) // Set loading state
            try {
                if (isNetworkAvailable()) { // Check if the network is available before making the API call
                    val apiResponse =
                        getSearchedNewsUseCase.execute(countryCode, category, searchQuery, page)
                    _searchNews.postValue(apiResponse)
                } else
                    _searchNews.postValue(Resource.Error(app
                            .getString(R.string.no_internet_connection)))
            } catch (e: Exception) {
                Log.e("NewsViewModel", "API error: ${e.message}",e)
                _searchNews.postValue(Resource.Error(e.message.toString()))
            }
        }

}