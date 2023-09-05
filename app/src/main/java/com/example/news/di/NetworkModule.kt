package com.example.news.di

import com.example.news.data.network.CovidApi
import com.example.news.data.network.NewsApi
import com.example.news.data.network.VideoApi
import com.example.news.data.network.WeatherApi
import com.example.news.utils.Constants.BASE_URL
import com.example.news.utils.Constants.BASE_URL_COVID
import com.example.news.utils.Constants.BASE_URL_VIDEO
import com.example.news.utils.Constants.BASE_URL_WEATHER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    @Named("News")
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @Named("Weather")
    fun provideRetrofitWeatherInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_WEATHER)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @Named("Video")
    fun provideRetrofitVideoInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_VIDEO)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @Named("Covid")
    fun provideRetrofitCovidInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_COVID)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideApiNewsService(@Named("News") retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }



    @Singleton
    @Provides
    fun provideApiWeatherService(@Named("Weather") retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApiVideoService(@Named("Video") retrofit: Retrofit): VideoApi {
        return retrofit.create(VideoApi::class.java)
    }

    @Singleton
    @Provides
    fun provideApiCovidService(@Named("Covid") retrofit: Retrofit): CovidApi {
        return retrofit.create(CovidApi::class.java)
    }

}