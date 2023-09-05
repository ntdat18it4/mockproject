package com.example.news.data

import com.example.news.data.database.NewsDao
import com.example.news.data.database.entities.FavoritesArticlesEntity
import com.example.news.data.database.entities.NewsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val newsDao: NewsDao) {

    fun readDatabase() : Flow<List<NewsEntity>> {
        return newsDao.readNews()
    }

    suspend fun insertNews(newsEntity: NewsEntity){
        newsDao.insertNews(newsEntity)
    }

    /** favorite article */

    fun readFavoriteArticles(): Flow<List<FavoritesArticlesEntity>> {
        return newsDao.readFavoriteArticles()
    }

    suspend fun insertFavoriteArticle(favoritesArticlesEntity: FavoritesArticlesEntity) {
        newsDao.insertFavoriteArticle(favoritesArticlesEntity)
    }

    suspend fun deleteFavoriteArticle(favoritesArticlesEntity: FavoritesArticlesEntity) {
        newsDao.deleteFavoriteArticle(favoritesArticlesEntity)
    }

    suspend fun deleteAllFavoriteArticle() {
        newsDao.deleteAllFavoriteArticle()
    }

}