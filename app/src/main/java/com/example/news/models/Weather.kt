package com.example.news.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Weather(
    val city: City,
    val list: ArrayList<DayWeather>
)

data class City(
    @SerializedName("name")
    @Expose
    val name: String,
)

data class DayWeather(
    val dt: Int,
    val temp: MainDay,
    val weather: ArrayList<WeatherInfo>
)

data class WeatherInfo(val description: String, val icon: String)

data class MainDay(
    @SerializedName("min")
    @Expose
    val tempMin: Double,
    @SerializedName("max")
    @Expose
    val tempMax: Double,
    @SerializedName("day")
    @Expose
    val tempDay: Double,
)