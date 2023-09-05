package com.example.news.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemVideo(
    val data: Data,
) : Parcelable

@Parcelize
data class Data(
    val data: List<Daum>,
) : Parcelable

@Parcelize
data class Daum(
    val id: Int,
    val title: String,
    val summary: String,
    val published: Int,
    val preview: Preview,
) : Parcelable

@Parcelize
data class Preview(
    val video: Video,
) :Parcelable

@Parcelize
data class Video(
    val poster: String,
    val source: Source,
) : Parcelable

@Parcelize
data class Source(
    @SerializedName("720")
    val n720: String,

) : Parcelable