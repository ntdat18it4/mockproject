package com.example.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ItemNewsRowTrendingBinding
import com.example.news.databinding.ItemVideoLayoutDetailBinding
import com.example.news.models.Articles
import com.example.news.models.Item
import com.example.news.ui.fragment.trending.TrendingFragmentDirections
import com.example.news.ui.fragment.video.PlayVideoFragmentDirections
import com.example.news.utils.NewsDiffUtil

class ItemTrendingNewsAdapter(val title : String) : RecyclerView.Adapter<ItemTrendingNewsAdapter.MyViewHolder>() {

    private var news = emptyList<Articles>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemNewsRowTrendingBinding.inflate(layoutInflater, parent, false))
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

    inner class MyViewHolder(private val binding: ItemNewsRowTrendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articles: Articles) {
            binding.article = articles
            binding.executePendingBindings()
            itemView.setOnClickListener {
                val action = TrendingFragmentDirections.actionTrendingFragmentToDetailsActivity(articles,title)
                it.findNavController().navigate(action)
            }
        }



    }
}