package com.ragdoll.newsapp.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.ragdoll.newsapp.R
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.databinding.FragmentNewsBinding
import com.ragdoll.newsapp.presentation.adapter.ArticleAdapter
import com.ragdoll.newsapp.presentation.ui.activity.MainActivity
import com.ragdoll.newsapp.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var articleAdapter: ArticleAdapter

    private var country = "us" // Default country code is set to "us" (United States).
    private var page = 1 // Default page number is set to 1.
    private var isScrolling = false // Flag to check if the user is currently scrolling.
    private var isLoading = false // Flag to check if new data is being loaded.
    private var isLastPage = false // Flag to check if the last page of data has been reached.
    private var pages = 0 // Variable to keep track of the total number of pages.

    // Use Safe Args to get the category passed from the previous fragment.
    private val args: NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        articleAdapter = (activity as MainActivity).articleAdapter

        articleAdapter.setOnItemClickListener { article ->
            if (article.url.isNotEmpty()) {
                val bundle = Bundle().apply {
                    putSerializable("selected_article", article)
                }
                findNavController().navigate(
                    R.id.action_newsFragment_to_detailsFragment,
                    bundle
                )
            } else {
                Toast.makeText(requireContext(), "Invalid article data", Toast.LENGTH_SHORT).show()
            }
        }

        val category = args.category
        initRecyclerView()
        viewNewsList()
        setSearchView()
        refreshNewsList()
        toolbar(category)
        setupAdMob()
    }

    private fun toolbar(category: String) {
        binding.titleToolBar.text = buildString {
            append(category)
            append(" News")
        }.replaceFirstChar { it.uppercase() }
    }

    private fun initRecyclerView() = binding.newsContent.articlesRv.apply {
        adapter = articleAdapter
        addOnScrollListener(this@NewsFragment.onScrollListener)
    }

    private fun viewNewsList() {
        // Get the news articles based on the category passed from the previous fragment.
        val category = args.category
        // Fetch the news headlines using the ViewModel.
        viewModel.getNewsHeadLines(country, category, page)
        viewModel.newsHeadLines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                    hideNoArticlesFound()
                }

                is Resource.Success -> {
                    hideProgressBar()
                    hideNoArticlesFound()
                    binding.newsContent.articlesRv.visibility = View.VISIBLE
                    response.data.let {
                        if (it.articles.isEmpty()) {
                            binding.newsContent.noArticlesTv.text =
                                getString(R.string.no_articles_is_found)
                            binding.newsContent.noArticlesTv.textSize = 20f
                            showNoArticlesFound()
                        } else {
                            hideNoArticlesFound()
                        }

                        articleAdapter.diffUtil.submitList(it.articles.toList())
                        Log.e("TAG", "viewNewsList: ${it.articles.toList()}")

                        pages =
                            if (it.totalResults % 20 == 0)
                                it.totalResults / 20
                            else
                                it.totalResults / 20 + 1 // If not divisible, add one more page

                        isLastPage = page == pages // Check if the current page is the last page
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    Log.e("TAG", "NewsHeadLines: ${response.message}")
                    response.message.let { message ->
                        binding.newsContent.apply {
                            noArticlesTv.text = buildString {
                                append(getString(R.string.an_error_occurred))
                                append(" $message")
                            }
                            noArticlesTv.textSize = 14f
                            articlesRv.visibility = View.INVISIBLE
                        }
                        showNoArticlesFound()
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        isLoading = true
        binding.newsContent.loadingProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        binding.newsContent.loadingProgressBar.visibility = View.INVISIBLE
    }

    private fun showNoArticlesFound() {
        binding.newsContent.noArticlesTv.visibility = View.VISIBLE
    }

    private fun hideNoArticlesFound() {
        binding.newsContent.noArticlesTv.visibility = View.INVISIBLE
    }

    /*
    This method is called for setting up the scroll listener for the RecyclerView.
    It helps in detecting when the user scrolls through the list of articles.
    */
    private val onScrollListener = object : RecyclerView.OnScrollListener() {

        // This method is called when the scroll state of the RecyclerView changes.
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        // This method is called when the RecyclerView is scrolled.
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager =
                binding.newsContent.articlesRv.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            // Check if the user has scrolled to the end of the list.
            val hasReachedToTheEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate =
                !isLoading && !isLastPage && hasReachedToTheEnd && isScrolling

            // If the user has scrolled to the end and pagination is needed, load more news articles.
            if (shouldPaginate) {
                page++
                viewModel.getNewsHeadLines(country, args.category, page)
                isScrolling = false
            }
        }
    }

    // This method is used to set up AdMob for displaying ads in the fragment.
    private fun setupAdMob() {
        val adView = AdView(requireContext())
        adView.adUnitId = "ca-app-pub-3940256099942544/9214589741" // Sample Test ad unit ID.
        // Request an anchored adaptive banner with a width of 360.
        adView.setAdSize(
            AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                requireContext(),
                360
            )
        )
        // Replace Ad container with new Ad view.
        binding.newsContent.adViewContainer.addView(adView)
        val adRequest = AdRequest.Builder().build() // Create an ad request.
        adView.loadAd(adRequest) // Load the ad into the AdView.
    }

    private fun refreshNewsList() {
        // Reset the page number and flags to load the news articles again.
        // Call the method to fetch and display the news articles.
        SwipeRefreshLayout.OnRefreshListener {
            binding.newsContent.swipeRefresh.isRefreshing = true
            page = 1
            isScrolling = false
            isLoading = false
            isLastPage = false
            viewModel.refreshNewsHeadLines(country, args.category, page)
            viewNewsList()
            binding.newsContent.swipeRefresh.isRefreshing = false
        }.let {
            binding.newsContent.swipeRefresh.setOnRefreshListener(it)
        }
    }

    private var searchJob: Job? = null
    private fun setSearchView() {
        val cat = args.category
        binding.articleSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
/*                searchJob?.cancel() // Cancel any previous search job
                if (!query.isNullOrBlank()) {
                    searchJob = viewLifecycleOwner.lifecycleScope.launch {
                        viewModel.searchedNews(country, cat, query.toString(), page)
                        viewSearchedNews()
                    }
                }*/
                // Solution for search query submission
                viewModel.searchedNews(country, cat, query.toString(), page)
                viewSearchedNews()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
/*                searchJob?.cancel()
                if (!newText.isNullOrBlank()) {
                    val delay = 2000L // Delay in milliseconds to avoid too many requests while typing
                    searchJob = viewLifecycleOwner.lifecycleScope.launch {
                        // For production, consider using a shorter debounce (e.g., 300–500ms) for better UX.
                        delay(500)
                        viewModel.searchedNews(country, cat, newText.toString(), page)
                        viewSearchedNews()
                    }
                } else {
                    initRecyclerView()
                    viewNewsList()
                }*/
                // Solution for search query submission
                MainScope().launch {
                    delay(2000)
                    viewModel.searchedNews(country, cat, newText.toString(), page)
                    viewSearchedNews()
                }
                return false
            }
        })

        binding.articleSV.setOnCloseListener {
            //searchJob?.cancel()
            initRecyclerView()
            viewNewsList()
            false
        }
    }

    // Search for articles by keyword
    private fun viewSearchedNews() {
        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                    hideNoArticlesFound()
                }

                is Resource.Success -> {
                    hideProgressBar()
                    hideNoArticlesFound()
                    binding.newsContent.articlesRv.visibility = View.VISIBLE
                    response.data.let {
                        if (it.articles.isEmpty()) {
                            binding.newsContent.noArticlesTv.text =
                                getString(R.string.no_articles_is_found)
                            binding.newsContent.noArticlesTv.textSize = 20f
                            showNoArticlesFound()
                        } else {
                            hideNoArticlesFound()
                        }

                        articleAdapter.diffUtil.submitList(it.articles.toList())
                        //Log.d("newsList", "viewNewsList: ${it.articles.toList()}")

                        pages =
                            if (it.totalResults % 20 == 0)
                                it.totalResults / 20
                            else
                                it.totalResults / 20 + 1 // If not divisible, add one more page

                        isLastPage = page == pages // Check if the current page is the last page
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    Log.e("TAG", "viewSearchedNews: ${response.message}")
                    response.message.let { message ->
                        binding.newsContent.apply {
                            noArticlesTv.text = buildString {
                                append(getString(R.string.an_error_occurred))
                                append(" $message")
                            }
                            noArticlesTv.textSize = 14f
                            articlesRv.visibility = View.INVISIBLE
                        }
                        showNoArticlesFound()
                    }
                }
            }
        }
    }
}


