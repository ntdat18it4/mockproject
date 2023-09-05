package com.example.news.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ItemVideoLayoutDetailBinding
import com.example.news.models.Data
import com.example.news.models.Daum
import com.example.news.ui.fragment.video.PlayVideoFragmentDirections
import com.example.news.utils.NewsDiffUtil
import java.text.SimpleDateFormat
import java.util.*

class ItemVideoAdapter() : RecyclerView.Adapter<ItemVideoAdapter.MyViewHolder>() {

    var video = emptyList<Daum>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemVideoLayoutDetailBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNews = video[position]
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int {
        return video.size
    }

    fun setData(newData: Data){
        val newsDiffUtil =
            NewsDiffUtil(video, newData.data)
        val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
        video = newData.data.asSequence().shuffled().take(10).toList()
        diffUtilResult.dispatchUpdatesTo(this)
    }



    inner class MyViewHolder(private val binding: ItemVideoLayoutDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daum) {
            binding.item = item
            binding.executePendingBindings()

            itemView.setOnClickListener {
                val action = PlayVideoFragmentDirections.actionPlayVideoFragmentSelf(item)
                it.findNavController().navigate(action)
            }


        }
    }
}