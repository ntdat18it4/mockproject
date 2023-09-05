package com.example.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.NewsRowLayoutBinding
import com.example.news.models.Articles
import com.example.news.models.Item
import com.example.news.ui.fragment.home.HomeFragmentDirections
import com.example.news.utils.NewsDiffUtil

class ItemHomeAdapter : RecyclerView.Adapter<ItemHomeAdapter.MyViewHolder>() {

    private var news = emptyList<Articles>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNews = news[position]
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    fun setData(newData: Item){
        val newsDiffUtil =
            NewsDiffUtil(news, newData.articles)
        val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
        news = newData.articles
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(private val binding: NewsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articles: Articles) {
            binding.article = articles
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent : ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }
}
