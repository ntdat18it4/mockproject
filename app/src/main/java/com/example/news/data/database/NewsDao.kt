package com.example.news.data.database

import androidx.room.*
import com.example.news.data.database.entities.FavoritesArticlesEntity
import com.example.news.data.database.entities.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    /** list news*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(newsEntity: NewsEntity)

    @Query("SELECT * FROM NEWS_TABLE ORDER BY id ASC")
    fun readNews() : Flow<List<NewsEntity>>

    /** favorite articles*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteArticle(favoritesArticlesEntity: FavoritesArticlesEntity)

    @Query("SELECT * FROM favorites_articles_table ORDER BY id ASC")
    fun readFavoriteArticles(): Flow<List<FavoritesArticlesEntity>>

    @Delete
    suspend fun deleteFavoriteArticle(favoritesArticlesEntity: FavoritesArticlesEntity)

    @Query("DELETE FROM favorites_articles_table")
    suspend fun deleteAllFavoriteArticle()

}