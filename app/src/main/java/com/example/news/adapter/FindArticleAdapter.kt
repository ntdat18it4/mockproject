package com.example.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ItemFindArticleBinding
import com.example.news.models.Articles
import com.example.news.models.Item
import com.example.news.utils.NewsDiffUtil

class FindArticleAdapter() :
    RecyclerView.Adapter<FindArticleAdapter.MyViewHolder>() {

    private var news = emptyList<Articles>()

    class MyViewHolder(private val binding: ItemFindArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(articles: Articles) {
            binding.article = articles
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFindArticleBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentArticle = news[position]
        holder.bind(currentArticle)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun setData(newData: Item) {
        val newsDiffUtil =
            NewsDiffUtil(news, newData.articles)
        val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
        news = newData.articles
        diffUtilResult.dispatchUpdatesTo(this)
    }
}