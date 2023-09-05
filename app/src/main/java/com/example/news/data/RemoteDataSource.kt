package com.example.news.data

import android.util.Log
import com.example.news.data.network.CovidApi
import com.example.news.data.network.NewsApi
import com.example.news.data.network.VideoApi
import com.example.news.data.network.WeatherApi
import com.example.news.models.Covid
import com.example.news.models.Item
import com.example.news.models.ItemVideo
import com.example.news.models.Weather
import com.example.news.utils.Constants
import com.example.news.utils.Constants.API_KEY
import com.example.news.utils.Constants.API_KEY_WEATHER
import com.example.news.utils.Constants.CNT
import com.example.news.utils.Constants.LANG
import com.example.news.utils.Constants.UNITS
import com.example.news.utils.Constants.appversionVideo
import com.example.news.utils.Constants.cVideo
import com.example.news.utils.Constants.cateidVideo
import com.example.news.utils.Constants.keyVideo
import com.example.news.utils.Constants.pVideo
import com.example.news.utils.Constants.platformVideo
import com.example.news.utils.Constants.uidVideo
import retrofit2.Response
import retrofit2.http.GET
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val newsApi: NewsApi,
    private val weatherApi: WeatherApi,
    private val videoApi: VideoApi,
    private val covidApi: CovidApi
) {

    suspend fun getAllNews(q: String): Response<Item> {
        return newsApi.getNews(q, Constants.SORT_BY, Constants.API_KEY)
    }

    suspend fun getWeather(q: String): Response<Weather> {
        return weatherApi.getWeather(q, UNITS, API_KEY_WEATHER, CNT, LANG)
    }

    suspend fun getAllVideos(): Response<ItemVideo> {
        return videoApi.getAllVideos(
            cateidVideo,
            pVideo,
            cVideo,
            appversionVideo,
            platformVideo,
            uidVideo,
            keyVideo,
        )
    }

    suspend fun getCovid(): Response<Covid>{
        return covidApi.getCovid()
    }

    suspend fun searchRecipes(q : String): Response<Item> {
        val currentTime: Date = Calendar.getInstance().time
        val sdf = SimpleDateFormat("yyyy-MM-dd")

        return newsApi.searchArticles(q,sdf.format(currentTime),"popularity", API_KEY)
    }

}