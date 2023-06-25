package com.example.vegasapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.vegasapp.R
import com.example.vegasapp.databinding.FragmentArticleBinding
import com.example.vegasapp.ui.NewsViewModel
import com.example.vegasapp.ui.VegasActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class ArticleFragment: Fragment(R.layout.fragment_article) {

    lateinit var binding: FragmentArticleBinding
    val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentArticleBinding.bind(view)
        val article = args.Article

        binding.webView.apply{
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }
    }
}