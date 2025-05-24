package com.ragdoll.newsapp.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ragdoll.newsapp.R
import com.ragdoll.newsapp.databinding.FragmentHomeBinding
import com.ragdoll.newsapp.presentation.ui.activity.MainActivity
import com.ragdoll.newsapp.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: NewsViewModel
    private var category = ""
    private var countryCode = "us"
    private var page = 1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        buttonClick()

    }

    private fun buttonClick() {
        binding.homeContent.apply {
            businessBtn.setOnClickListener {
                category = "business"
                viewModel.getNewsHeadLines(countryCode, category, page)
                navigation(category = category, countryCode = countryCode, page = page)
            }
            entertainmentBtn.setOnClickListener {
                category = "entertainment"
                viewModel.getNewsHeadLines(countryCode, category, page)
                navigation(category = category, countryCode = countryCode, page = page)
            }
            healthBtn.setOnClickListener {
                category = "health"
                viewModel.getNewsHeadLines(countryCode, category, page)
                navigation(category = category, countryCode = countryCode, page = page)
            }
            scienceBtn.setOnClickListener {
                category = "science"
                viewModel.getNewsHeadLines(countryCode, category, page)
                navigation(category = category, countryCode = countryCode, page = page)
            }
            sportsBtn.setOnClickListener {
                category = "sports"
                viewModel.getNewsHeadLines(countryCode, category, page)
                navigation(category = category, countryCode = countryCode, page = page)
            }
            techBtn.setOnClickListener {
                category = "technology"
                viewModel.getNewsHeadLines(countryCode, category, page)
                navigation(category = category, countryCode = countryCode, page = page)
            }
        }
    }

    private fun navigation(category: String, countryCode: String, page: Int) {
        findNavController().navigate(
            R.id.action_homeFragment_to_newsFragment,
            bundleOf("category" to category, "countryCode" to countryCode, "page" to page),
        )
    }

}