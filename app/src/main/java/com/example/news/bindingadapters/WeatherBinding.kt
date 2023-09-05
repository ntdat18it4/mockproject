package com.example.news.bindingadapters

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.news.models.MainDay

class WeatherBinding {

    companion object{

        @SuppressLint("SetTextI18n")
        @BindingAdapter("fillTemp")
        @JvmStatic
        fun fillTemp(textView: TextView,mainDay: MainDay) {
            textView.text = "${mainDay.tempDay.toInt()}°C / ${mainDay.tempMin.toInt()}°C - ${mainDay.tempMax.toInt()}°C"
        }
    }
}