package com.ragdoll.newsapp.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ragdoll.newsapp.R
import com.ragdoll.newsapp.databinding.FragmentHomeBinding
import com.ragdoll.newsapp.presentation.ui.activity.MainActivity
import com.ragdoll.newsapp.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: NewsViewModel
    private var category = ""
    private var countryCode = "us"
    private var page = 1
    private var mInterstitialAd: InterstitialAd? = null

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
        initViews()


    }

    private fun initViews() {
        intiMobileAds() // Initialize the InterstitialAd
        loadAdvertisement() // Load the InterstitialAd
        buttonClick() // Show the InterstitialAd if available when a button is clicked
    }

    private fun buttonClick(){
        binding.homeContent.apply {
            businessBtn.setOnClickListener {
                showAdvertisement()
                category = "business"
                viewModel.getNewsHeadLines(countryCode, category, page)
                // navigation(category = category, countryCode = countryCode, page = page)
            }
            entertainmentBtn.setOnClickListener {
                category = "entertainment"
                viewModel.getNewsHeadLines(countryCode, category, page)
                showAdvertisement()
                //navigation(category = category, countryCode = countryCode, page = page)
            }
            healthBtn.setOnClickListener {
                category = "health"
                viewModel.getNewsHeadLines(countryCode, category, page)
                showAdvertisement()
                //navigation(category = category, countryCode = countryCode, page = page)
            }
            scienceBtn.setOnClickListener {
                category = "science"
                viewModel.getNewsHeadLines(countryCode, category, page)
                showAdvertisement()
                //navigation(category = category, countryCode = countryCode, page = page)
            }
            sportsBtn.setOnClickListener {
                category = "sports"
                viewModel.getNewsHeadLines(countryCode, category, page)
                showAdvertisement()
                //navigation(category = category, countryCode = countryCode, page = page)
            }
            techBtn.setOnClickListener {
                category = "technology"
                viewModel.getNewsHeadLines(countryCode, category, page)
                //navigation(category = category, countryCode = countryCode, page = page)
                showAdvertisement()
            }
        }
    }

    private fun navigation(category: String, countryCode: String, page: Int) {
        findNavController().navigate(
            R.id.action_homeFragment_to_newsFragment,
            bundleOf("category" to category, "countryCode" to countryCode, "page" to page),
        )
    }

    // Initialize the Google Mobile Ads SDK on a background thread.
    fun intiMobileAds() = CoroutineScope(Dispatchers.IO).launch {
        // Initialize Mobile Ads SDK
        MobileAds.initialize(requireActivity()) {
            // Optional: You can add any initialization code here if needed
        }
    }

    fun loadAdvertisement() = CoroutineScope(Dispatchers.Main).launch {
        // Load an InterstitialAd
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireActivity(),
            "ca-app-pub-3940256099942544/1033173712", // Replace with your actual ad unit ID
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d("TAG", adError.message)
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("TAG", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }

    private fun showAdvertisement() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    // Called when fullscreen content is dismissed.
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    Log.d("TAG", "Ad was dismissed.")
                    mInterstitialAd = null
                    navigation(category, countryCode, page)
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    // Called when fullscreen content failed to show.
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    Log.d("TAG", "Ad failed to show.")
                    mInterstitialAd = null
                    navigation(category, countryCode, page)
                }

            }
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.")
            navigation(category, countryCode, page)
        }

    }

}

