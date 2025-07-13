package com.ragdoll.newsapp.presentation.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ragdoll.newsapp.R
import com.ragdoll.newsapp.databinding.ActivityMainBinding
import com.ragdoll.newsapp.presentation.adapter.ArticleAdapter
import com.ragdoll.newsapp.presentation.viewmodel.NewsViewModel
import com.ragdoll.newsapp.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: NewsViewModelFactory
    @Inject
    lateinit var articleAdapter: ArticleAdapter
    lateinit var viewModel: NewsViewModel
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


        /* Was Done in NewsFragment.kt
        // Get the news articles.
        loadingNews()
        // Set up AdMob Create a new ad view.
        val adView = AdView(this)
        adView.adUnitId = "ca-app-pub-3940256099942544/9214589741" // Sample Test ad unit ID.
        // Request an anchored adaptive banner with a width of 360.
        adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 360))
        // Replace ad container with new ad view.
        binding.adViewContainer.addView(adView)
        // Load the ad.
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)*/

        // Set up the navigation component.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // Set up the bottom navigation view with the navController.
        binding.bottomNavigationView.setupWithNavController(navController)
        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
    }

    /* Was Done in NetModule.kt and NewsFragment.kt
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
                    *//*if (articles.isNullOrEmpty()) {
                        binding.noArticlesTv.isVisible = true
                    } else {
                        //Log.d("trace", "Article: ${articles?.get(0)}")
                        val adapter = ArticleAdapter(this@MainActivity, articles)
                        binding.articlesRv.adapter = adapter
                        binding.articlesRv.isVisible = false
                    }*//*

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
    }*/
}