package com.example.news.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.news.data.Repository
import com.example.news.data.database.entities.FavoritesArticlesEntity
import com.example.news.data.database.entities.NewsEntity
import com.example.news.models.Item
import com.example.news.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** internet */

    val checkInternet: MutableLiveData<Boolean> = MutableLiveData(true)
    var checkHome : Boolean? = null

    /** database */
    private val _readNews: MutableLiveData<List<NewsEntity>> =
        repository.local.readDatabase().asLiveData() as MutableLiveData<List<NewsEntity>>
    val readNews: LiveData<List<NewsEntity>>
        get() = _readNews

    private fun insertNews(newsEntity: NewsEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertNews(newsEntity = newsEntity)
    }

    val readFavoriteArticles: LiveData<List<FavoritesArticlesEntity>> = repository.local.readFavoriteArticles().asLiveData()

    fun insertFavoriteArticle(favoritesEntity : FavoritesArticlesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteArticle(favoritesEntity)
        }

    fun deleteFavoriteArticle(favoritesEntity: FavoritesArticlesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteArticle(favoritesEntity)
        }

    private fun deleteAllFavoriteArticles() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteArticle()
        }

    /** retrofit */
    var newsResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()

    fun getNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q)
    }

    private suspend fun getNewsSafeCall(q: String) {
        newsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getAllNews(q) // response
                newsResponse.value = handleNewsResponse(response)

                val item = newsResponse.value!!.data
                if (item != null) {
                    offlineCacheNews(item)
                }
            } catch (e: Exception) {
                newsResponse.value = NetworkResult.Error("News not found.")
            }
        } else {
            newsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheNews(item: Item) {
        val newsEntity = NewsEntity(item)
        insertNews(newsEntity)
    }

    private fun handleNewsResponse(response: Response<Item>): NetworkResult<Item>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.articles.isEmpty() -> {
                return NetworkResult.Error("Articles not found.")
            }
            response.isSuccessful -> {
                val item = response.body()
                return NetworkResult.Success(item!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}