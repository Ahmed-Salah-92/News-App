package com.ragdoll.newsapp.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ragdoll.newsapp.R
import com.ragdoll.newsapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    // Use Safe Args to get the article passed from the previous fragment.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        val args: DetailsFragmentArgs by navArgs()
        val article = args.selectedArticle

        binding.infoWV.apply {
            webViewClient = WebViewClient() // Set a WebViewClient to handle page navigation
//            settings.javaScriptEnabled = true // Enable JavaScript for the WebView
//            settings.domStorageEnabled = true // Enable DOM storage for the WebView
//            settings.loadWithOverviewMode = true // Load the page in overview mode
//            settings.useWideViewPort = true // Use wide viewport for better scaling
//            settings.setSupportZoom(true) // Enable zoom support
//            settings.builtInZoomControls = true // Enable built-in zoom controls
//            settings.displayZoomControls = false // Hide zoom controls on the screen
            if (article.url.isNotEmpty())
                loadUrl(article.url) // Load the URL of the selected article if it is not empty
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.infoWV.destroy() // Destroy the WebView to free up resources
    }
}