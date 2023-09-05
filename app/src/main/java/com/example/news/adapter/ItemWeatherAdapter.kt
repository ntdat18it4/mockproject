package com.example.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.news.R
import com.example.news.databinding.ItemWeatherBinding
import com.example.news.models.Weather
import com.example.news.models.DayWeather
import com.example.news.utils.NewsDiffUtil
import com.example.news.utils.toDate

class ItemWeatherAdapter : RecyclerView.Adapter<ItemWeatherAdapter.MyViewHolder>() {

    private var weathers = emptyList<DayWeather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentDayWeather = weathers[position]
        holder.bind(currentDayWeather)
    }

    override fun getItemCount(): Int {
        return weathers.size
    }

    fun setData(weather: Weather){
        val newsDiffUtil =
            NewsDiffUtil(weathers, weather.list)
        val diffUtilResult = DiffUtil.calculateDiff(newsDiffUtil)
        weathers = weather.list
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listDayWeather: DayWeather) {
            binding.tvDay.text = listDayWeather.dt.toDate()

            val icon = listDayWeather.weather[0].icon

            binding.imgDay.load("https://openweathermap.org/img/wn/${listDayWeather.weather[0].icon}@2x.png"){
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }

            binding.tempMin.text = listDayWeather.temp.tempMin.toInt().toString()+"°C"
            binding.tempMin.text = listDayWeather.temp.tempMax.toInt().toString()+"°C"
        }

        companion object{
            fun from(parent : ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWeatherBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

}
