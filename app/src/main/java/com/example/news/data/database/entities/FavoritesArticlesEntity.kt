package com.example.news.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news.utils.Constants
import com.example.news.utils.Constants.FAVORITES_ARTICLES_TABLE

@Entity(tableName = FAVORITES_ARTICLES_TABLE)
data class FavoritesArticlesEntity(
    @ColumnInfo(name = Constants.ID_COLUMN)
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = Constants.NAME_COLUMN)
    var name: String,
    @ColumnInfo(name = Constants.TITLE_COLUMN)
    var title: String,
    @ColumnInfo(name = Constants.IMAGE_COLUMN)
    var urlToImage: String,
    @ColumnInfo(name = Constants.DATE_COLUMN)
    var publishedAt: String,
    @ColumnInfo(name = Constants.URL_COLUMN)
    var url: String,
)