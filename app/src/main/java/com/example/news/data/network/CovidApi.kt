package com.example.news.data.network


import com.example.news.models.Covid
import retrofit2.Response
import retrofit2.http.GET

interface CovidApi {
    @GET("corona/api")
    suspend fun getCovid(): Response<Covid>
}