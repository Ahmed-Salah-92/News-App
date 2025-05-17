package com.ragdoll.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.ragdoll.newsapp.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    // View Binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Get the news articles
        loadingNews()
    }

    private fun loadingNews() {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Get the category from the intent
        val category = intent.getStringExtra("category")!!
        // get the country code from the shared preferences
        val countryCode = getSharedPreferences("settings", MODE_PRIVATE)
            .getString("countryCode", "us")!!

        val callback = retrofit.create(NewsAPICallable::class.java)
        callback.getNews(countryCode, category).enqueue(object : Callback<News> {
            override fun onResponse(p0: Call<News?>, p1: Response<News?>) {
                binding.loadingProgressBar.isVisible = false
                if (p1.isSuccessful) {
                    val articles = p1.body()?.articles
                    /*if (articles.isNullOrEmpty()) {
                        binding.noArticlesTv.isVisible = true
                    } else {
                        //Log.d("trace", "Article: ${articles?.get(0)}")
                        val adapter = ArticleAdapter(this@MainActivity, articles)
                        binding.articlesRv.adapter = adapter
                        binding.articlesRv.isVisible = false
                    }*/

                    if (articles.isNullOrEmpty()) {
                        binding.noArticlesTv.isVisible = true
                    } else {
                        //Log.d("trace", "Article: ${articles?.get(0)}")
                        binding.articlesRv.adapter = ArticleAdapter(this@MainActivity, articles)
                        binding.noArticlesTv.isVisible = false
                    }
                }
            }

            override fun onFailure(p0: Call<News?>, p1: Throwable) {
                binding.loadingProgressBar.isVisible = true
                //Log.d("trace", "Error: ${p1.message}")
            }
        })
    }
}