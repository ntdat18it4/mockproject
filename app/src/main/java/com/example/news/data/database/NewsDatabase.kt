package com.example.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news.data.database.entities.FavoritesArticlesEntity
import com.example.news.data.database.entities.NewsEntity

@Database(
    entities = [NewsEntity::class,FavoritesArticlesEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase  : RoomDatabase(){

    abstract fun newsDao() : NewsDao

}