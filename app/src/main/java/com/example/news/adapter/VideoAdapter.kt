package com.example.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ItemLayoutVideoBinding
import com.example.news.models.Daum
import com.example.news.models.ItemVideo
import com.example.news.ui.fragment.video.VideoFragmentDirections
import com.example.news.utils.NewsDiffUtil
import com.example.news.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class VideoAdapter(val callback : (item :Daum) -> Unit) : RecyclerView.Adapter<VideoAdapter.MyViewHolder>() {

    private var video = emptyList<Daum>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemLayoutVideoBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNews = video[position]
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int {
        return video.size
    }

    fun setData(newData: ItemVideo) {
        val newsDiffUtil =
            NewsDiffUtil(video, newData.data.data)
        val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
        video = newData.data.data
        diffUtilResult.dispatchUpdatesTo(this)
    }

    inner class MyViewHolder(private val binding: ItemLayoutVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Daum) {
            binding.item = item
            binding.executePendingBindings()

            itemView.setOnClickListener {
                callback(item)
            }
        }
    }





}
