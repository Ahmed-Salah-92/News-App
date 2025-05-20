package com.ragdoll.newsapp.presentation.viewmodel

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ragdoll.newsapp.data.model.APIResponse
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.domain.usecase.GetNewsHeadLinesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val app: Application,
    private val getNewsHeadLinesUseCase: GetNewsHeadLinesUseCase,
) : AndroidViewModel(app) {

    val newsHeadLines: MutableLiveData<Resource<APIResponse>> = MutableLiveData()

    fun getNewsHeadLines(countryCode: String, category: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {

            // Check if the network is available before making the API call
            newsHeadLines.postValue(Resource.Loading()) // Set loading state
            try {
                if (isNetworkAvailable(app)) {
                    val apiResponse = getNewsHeadLinesUseCase.execute(countryCode, category, page)
                    newsHeadLines.postValue(apiResponse)
                } else
                    newsHeadLines.postValue(Resource.Error("No Internet Connection"))
            } catch (e: Exception) {
                newsHeadLines.postValue(Resource.Error(e.message.toString()))
            }

        }

    private fun isNetworkAvailable(app: Application): Boolean { // Implement network availability check

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
    }

}