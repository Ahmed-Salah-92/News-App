package com.ragdoll.newsapp.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ragdoll.newsapp.R
import com.ragdoll.newsapp.data.util.Resource
import com.ragdoll.newsapp.databinding.FragmentNewsBinding
import com.ragdoll.newsapp.presentation.adapter.ArticleAdapter
import com.ragdoll.newsapp.presentation.ui.activity.MainActivity
import com.ragdoll.newsapp.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var articleAdapter: ArticleAdapter
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
        val category = args.category
        initRecyclerView()
        viewNewsList()
        toolbar(category)
    }

    private fun toolbar(category: String) {
        binding.titleToolBar.text = buildString {
            append(category)
            append(" News")
        }.replaceFirstChar { it.uppercase() }
    }

    private fun initRecyclerView() {
        articleAdapter = ArticleAdapter()
        binding.newsContent.articlesRv.adapter = articleAdapter
    }

    private fun viewNewsList() {
        // Get the news articles based on the category passed from the previous fragment.
        val category = args.category
        viewModel.getNewsHeadLines("us", category, 1)
        viewModel.newsHeadLines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Loading -> {
                    showProgressBar()
                    hideNoArticlesFound()
                }

                is Resource.Success -> {
                    hideProgressBar()
                    hideNoArticlesFound()
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
                    }
                }

                is Resource.Error -> {
                    hideProgressBar()
                    response.message.let { message ->
                        binding.newsContent.noArticlesTv.text = buildString {
                            append(getString(R.string.an_error_occurred))
                            append(message)
                        }
                        binding.newsContent.noArticlesTv.textSize = 14f
                        showNoArticlesFound()
                    }
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.newsContent.loadingProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.newsContent.loadingProgressBar.visibility = View.INVISIBLE
    }

    private fun showNoArticlesFound() {
        binding.newsContent.noArticlesTv.visibility = View.VISIBLE
    }

    private fun hideNoArticlesFound() {
        binding.newsContent.noArticlesTv.visibility = View.INVISIBLE
    }
}


