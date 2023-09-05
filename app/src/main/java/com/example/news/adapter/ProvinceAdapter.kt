package com.example.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.news.databinding.ItemProvinceBinding

class ProvinceAdapter (private val province: List<String>, val callback:(item: String) -> Unit) : RecyclerView.Adapter<ProvinceAdapter.MyViewHolder>() {

    private lateinit var binding: ItemProvinceBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = ItemProvinceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentNews = province[position]
        holder.bind(currentNews)
    }

    override fun getItemCount(): Int {
        return province.size
    }


    inner class MyViewHolder(private val binding: ItemProvinceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(province: String) {
            binding.tvLocation.text = province

            itemView.setOnClickListener {
                callback(province)
            }

        }

    }
}