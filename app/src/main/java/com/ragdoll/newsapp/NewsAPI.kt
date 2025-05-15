package com.ragdoll.newsapp

import retrofit2.Call
import retrofit2.http.GET

interface NewsAPICallable {

    // Endpoint to get the news articles
    @GET("/v2/top-headlines?country=us&category=general&apiKey=d9e17e996caa435aae75ca77e76727b6")
    fun getNews() : Call<News>
}