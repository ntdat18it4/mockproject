package com.example.news.data.network

import com.example.news.models.Item
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    //https://newsapi.org/v2/everything?q=dịch covid&sortBy=publishedAt&apiKey=eb4ad6db55664fed859956dd12c3e9df
    @GET("everything")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String,
    ): Response<Item>

    @GET("everything")
    suspend fun searchArticles(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy : String,
        @Query("apiKey") apiKey: String,
    ) : Response<Item>

}