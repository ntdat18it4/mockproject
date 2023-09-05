package com.example.news.data.network

import com.example.news.models.ItemVideo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {
    @GET("/v2/videos")
    suspend fun getAllVideos(
        @Query("cateid") query: Int ,
        @Query("p") p: Int,
        @Query("c") c: Int ,
        @Query("appversion") appversion: Int ,
        @Query("platform") platform: String ,
        @Query("uid") uid: String ,
        @Query("key") key: String ,
    ): Response<ItemVideo>
}

