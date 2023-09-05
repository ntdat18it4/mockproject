package com.example.news.ui.act

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.example.news.R
import com.example.news.data.database.entities.FavoritesArticlesEntity
import com.example.news.databinding.ActivityDetailsBinding
import com.example.news.models.Articles
import com.example.news.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private val mainViewModel: MainViewModel by viewModels()

    private val args: DetailsActivityArgs by navArgs()

    private lateinit var currentArticles: Articles

    private var articleSaved = false
    private var savedArticleId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentArticles = args.articles!!

        supportActionBar!!.title = args.title

        setupWebView()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun share() {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            currentArticles.url
        ) // Simple text and URL to share

        sendIntent.type = "text/plain"
        this.startActivity(sendIntent)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        binding.webView.apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(currentArticles.url)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorites_menu)
        checkSavedNews(menuItem!!)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        try {
            if (item.itemId == android.R.id.home) {
                this.finish()
            } else if (item.itemId == R.id.save_to_favorites_menu && !articleSaved) {
                saveToFavorites(item)
            } else if (item.itemId == R.id.save_to_favorites_menu && articleSaved) {
                removeFromFavorites(item)
            } else if (item.itemId == R.id.share_to_favorites_menu && !articleSaved) {
                share()
            }
        } catch (e: Exception) {
            Log.d("ducpt19", "${e.stackTrace}")
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity =
            args.articles?.let {
                FavoritesArticlesEntity(
                    0,
                    it.source!!.name,
                    it.title,
                    it.urlToImage,
                    it.publishedAt,
                    it.url
                )
            }
        mainViewModel.insertFavoriteArticle(favoritesEntity!!)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Article saved.")
        articleSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoritesArticlesEntity(
                savedArticleId,
                args.articles!!.source!!.name,
                args.articles!!.title,
                args.articles!!.urlToImage,
                args.articles!!.publishedAt,
                args.articles!!.url,
            )
        mainViewModel.deleteFavoriteArticle(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar("Removed from Favorites.")
        articleSaved = false
    }

    private fun checkSavedNews(menuItem: MenuItem) {
        mainViewModel.readFavoriteArticles.observe(this) { favoritesEntity ->
            try {
                for (savedArticle in favoritesEntity) {
                    if (savedArticle.title == args.articles!!.title) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedArticleId = savedArticle.id
                        articleSaved = true
                    } else {
                        changeMenuItemColor(menuItem, R.color.white)
                    }
                }
            } catch (e: java.lang.Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}