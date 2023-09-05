package com.example.news.data.network

import com.example.news.models.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String,
        @Query("appid") appid: String,
        @Query("cnt") time: Int,
        @Query("lang") lang: String
    ): Response<Weather>


}