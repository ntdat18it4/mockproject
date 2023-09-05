package com.example.news.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Articles(var source: Sources?,
                    var title: String,
                    var description: String?,
                    var urlToImage: String,
                    var publishedAt: String,
                    var url: String) : Parcelable