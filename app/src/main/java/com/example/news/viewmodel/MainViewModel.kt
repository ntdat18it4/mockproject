package com.example.news.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.*
import com.example.news.data.Repository
import com.example.news.data.database.entities.FavoritesArticlesEntity
import com.example.news.data.database.entities.NewsEntity
import com.example.news.models.Covid
import com.example.news.models.Item
import com.example.news.models.ItemVideo
import com.example.news.models.Weather
import com.example.news.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    private val sharedPreferences: SharedPreferences,
    application: Application
) : AndroidViewModel(application) {

    /** internet */
    val checkInternet: MutableLiveData<Boolean> = MutableLiveData(true)

    var checkWeather: Boolean? = null

    /** ================================= */

    /** database */
    var checkReadDatabase = false
    private val _readNews: MutableLiveData<List<NewsEntity>> =
        repository.local.readDatabase().asLiveData() as MutableLiveData<List<NewsEntity>>
    val readNews: LiveData<List<NewsEntity>>
        get() = _readNews

    private fun insertNews(newsEntity: NewsEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertNews(newsEntity = newsEntity)
    }

    val readFavoriteArticles: LiveData<List<FavoritesArticlesEntity>> =
        repository.local.readFavoriteArticles().asLiveData()

    fun insertFavoriteArticle(favoritesEntity: FavoritesArticlesEntity) =
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
    /** ================================= */

    /** retrofit news */
    var newsHomeResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsLatestResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsVietnamResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsWorldResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsEntertainmentResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsSportResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsScienceResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsLawResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsEducationResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsHealthResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsLifeResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsTravelResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsIdeaResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var newsTalkResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()
    var searchedNewsResponse: MutableLiveData<NetworkResult<Item>> = MutableLiveData()

    fun getHomeNews(q: String) = viewModelScope.launch {
        getNewsHomeSafeCall(q,newsHomeResponse)
    }

    fun searchNews(q: String) = viewModelScope.launch {
        searchNewsSafeCall(q)
    }

    fun getLatestNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsLatestResponse)
    }

    fun getVietnamNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsVietnamResponse)
    }

    fun getWorldNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsWorldResponse)
    }

    fun getEntertainmentNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsEntertainmentResponse)
    }

    fun getSportNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsSportResponse)
    }

    fun getScienceNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsScienceResponse)
    }

    fun getLawNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsLawResponse)
    }

    fun getEducationNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsEducationResponse)
    }

    fun getHealthNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsHealthResponse)
    }

    fun getLifeNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsLifeResponse)
    }

    fun getTravelNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsTravelResponse)
    }

    fun getIdeaNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsIdeaResponse)
    }

    fun getTalkNews(q: String) = viewModelScope.launch {
        getNewsSafeCall(q,newsTalkResponse)
    }

    private suspend fun getNewsHomeSafeCall(
        q: String,
        newsResponse : MutableLiveData<NetworkResult<Item>>
    ) {
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

    private suspend fun getNewsSafeCall(
        q: String,
        newsResponse : MutableLiveData<NetworkResult<Item>>
    ) {
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

    private suspend fun searchNewsSafeCall(q: String) {
        searchedNewsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(q)
                searchedNewsResponse.value = handleNewsResponse(response)
            } catch (e: java.lang.Exception) {
                searchedNewsResponse.value = NetworkResult.Error("News not found.")
            }
        } else {
            searchedNewsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
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

    private fun offlineCacheNews(item: Item) {
        val newsEntity = NewsEntity(item)
        insertNews(newsEntity)
    }

    /** ================================= */

    /** retrofit weather */


    val weatherResponse: MutableLiveData<NetworkResult<Weather>> = MutableLiveData()

    fun getWeather(q: String) = viewModelScope.launch {
        getWeatherSafeCall(q)
    }

    private suspend fun getWeatherSafeCall(q: String) {
        weatherResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getWeather(q) // response
                weatherResponse.value = handleWeatherResponse(response)

            } catch (e: Exception) {
               Log.d("ducpt20",e.stackTrace.toString())
            }
        } else {
            newsHomeResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleWeatherResponse(response: Response<Weather>): NetworkResult<Weather>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.list.isEmpty() -> {
                return NetworkResult.Error("Data not found.")
            }
            response.isSuccessful -> {
                val dayWeather = response.body()
                return NetworkResult.Success(dayWeather!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    /** ================================= */

    /** retrofit Video */
    var checkVideo : Boolean? = null
    val videosResponse: MutableLiveData<NetworkResult<ItemVideo>> = MutableLiveData()
    fun getVideos() = viewModelScope.launch {
        getVideosSafeCall()
    }

    private suspend fun getVideosSafeCall() {
        videosResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getAllVideos() // response
                videosResponse.value = handleVideosResponse(response)
            } catch (e: Exception) {
                Log.d("ducpt19", e.stackTrace.toString())
                videosResponse.value = NetworkResult.Error("Videos not found.")
            }
        } else {
            videosResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleVideosResponse(response: Response<ItemVideo>): NetworkResult<ItemVideo>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.data.data.isEmpty() -> {
                return NetworkResult.Error("Videos not found ")
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

    /** ================================= */

    /** retrofit Covid */
    var checkCovid: Boolean = false
    val covidResponse: MutableLiveData<NetworkResult<Covid>> = MutableLiveData()
    fun getCovid() = viewModelScope.launch {
        getCovidSafeCall()
    }

    private suspend fun getCovidSafeCall() {
        covidResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getCovid() // response
                covidResponse.value = handleCovidResponse(response)

            } catch (e: Exception) {
                videosResponse.value = NetworkResult.Error("Data Covid not found.")
            }
        } else {
            videosResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handleCovidResponse(response: Response<Covid>): NetworkResult<Covid> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.countries.isEmpty() -> {
                return NetworkResult.Error("data covid not found ")
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

    var checkHome: Boolean? = null
    var checkLatest: Boolean? = null
    var checkVietnam: Boolean? = null
    var checkWorld: Boolean? = null
    var checkEntertainment: Boolean? = null
    var checkSport: Boolean? = null
    var checkScience: Boolean? = null
    var checkLaw: Boolean? = null
    var checkEducation: Boolean? = null
    var checkHealth: Boolean? = null
    var checkLife: Boolean? = null
    var checkTravel: Boolean? = null
    var checkIdea: Boolean? = null
    var checkTalk: Boolean? = null

    /** luu tinh thanh */

    var isChangeProvince = MutableLiveData<Boolean>(false)

    fun saveProvince(province : String){
        sharedPreferences.edit().putString("province", province).apply()

    }

    fun getProvince() : String {
        val province = sharedPreferences.getString("province", "Đà Nẵng").toString()
        if(province == "Thành Phố Hồ Chí Minh"){
            return "Hồ Chí Minh"
        }
        return province
    }
}