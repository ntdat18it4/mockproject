package com.example.news.bindingadapters

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.news.R
import com.example.news.models.Articles
import com.example.news.ui.fragment.find.FindFragmentDirections
import com.example.news.ui.fragment.home.HomeFragmentDirections
import com.example.news.ui.fragment.trending.TrendingFragmentDirections
import com.example.news.utils.Constants
import com.example.news.utils.toDate
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class NewsRowBinding {

    companion object{

        @BindingAdapter("onNewsClickListener")
        @JvmStatic
        fun onNewsClickListener(newsRowLayout: ConstraintLayout, articles: Articles) {
            Log.d("onRecipeClickListener", "CALLED")
            newsRowLayout.setOnClickListener {
                try {
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailsActivity(articles,"Home")
                    newsRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onNewsClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("onNewsTrendingClickListener")
        @JvmStatic
        fun onNewsTrendingClickListener(newsRowLayout: ConstraintLayout, articles: Articles) {
            Log.d("onRecipeClickListener", "CALLED")
            newsRowLayout.setOnClickListener {
                try {
                    val action = TrendingFragmentDirections.actionTrendingFragmentToDetailsActivity(articles,"Trending")
                    newsRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onNewsClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("setTimeFormat")
        @JvmStatic
        fun setTimeFormat(textView: TextView, time: String){
            textView.text = Constants.dateToTimeFormat(time)
        }

        @BindingAdapter("setDayFormat")
        @JvmStatic
        fun setDayFormat(textView: TextView, time: String){
            textView.text = Constants.dateFormat(time)
        }

        @BindingAdapter("onNewsFindClickListener")
        @JvmStatic
        fun onNewsFindClickListener(newsRowLayout: ConstraintLayout, articles: Articles) {
            Log.d("onRecipeClickListener", "CALLED")
            newsRowLayout.setOnClickListener {
                try {
                    val action = FindFragmentDirections.actionFindFragmentToDetailsActivity(articles,"Detail")
                    newsRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onNewsClickListener", e.toString())
                }
            }
        }
    }
}