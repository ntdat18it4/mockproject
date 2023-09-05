package com.example.news.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

class VideoBinding {
    companion object{

        @BindingAdapter("setDayFormatVideo")
        @JvmStatic
        fun setDayFormatVideo(textView: TextView, time: Int){
            val sdf = SimpleDateFormat("EEEE yyyy-MM-dd")
            val netDate = Date(time.toLong() * 1000)
            textView.text = sdf.format(netDate)
        }

        @BindingAdapter("setHourFormatVideo")
        @JvmStatic
        fun setHourFormatVideo(textView: TextView, time: Int){
            val sdf = SimpleDateFormat("HH")
            val netDate = Date(time.toLong() * 1000)
            textView.text = sdf.format(netDate)+ " giờ trước "
        }
    }
}