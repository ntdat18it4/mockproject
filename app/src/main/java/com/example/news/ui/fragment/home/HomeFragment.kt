package com.example.news.ui.fragment.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.news.R
import com.example.news.adapter.ItemHomeAdapter
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.utils.Constants
import com.example.news.utils.NetworkResult
import com.example.news.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val mAdapter by lazy { ItemHomeAdapter() }
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun initView() {
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        setupRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            requestApiData()
            requestWeatherApiData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.imgIconFind.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFindFragment()
            findNavController().navigate(action)
        }

        handleCheckWeather()
    }

    private fun handleCheckWeather() {
        binding.layoutWeather.setOnClickListener {
            if (mainViewModel.checkInternet.value == true) {
                findNavController().navigate(R.id.action_homeFragment_to_weatherBottomSheet)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvHome.adapter = mAdapter
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
    }

    @SuppressLint("SetTextI18n")
    override fun obServerLivedata() {
        mainViewModel.checkInternet.observe(viewLifecycleOwner) { checkInternet ->
            if (!checkInternet) {
                mainViewModel.checkHome = false
                mainViewModel.newsHomeResponse.value =
                    NetworkResult.Error(message = "No Internet Connection.")

                if (mainViewModel.weatherResponse.value!!.data != null) {
                    val dayWeather = mainViewModel.weatherResponse.value!!.data
                    val city = dayWeather!!.city
                    if (city.name == "Thành phố Hồ Chí Minh") {
                        binding.tvCity.text = "Hồ Chí Minh"
                    } else {
                        binding.tvCity.text = city.name
                    }

                    val day = dayWeather.list[0]
                    binding.tvTemp.text =
                        "${day.temp.tempDay.toInt()}°C / ${day.temp.tempMin.toInt()}°C - ${day.temp.tempMax.toInt()}°C"
                } else {
                    binding.tvCity.visibility = View.INVISIBLE
                    binding.tvTemp.visibility = View.INVISIBLE
                }

            } else {
                if (binding.tvCity.visibility == View.INVISIBLE) {
                    binding.tvCity.visibility = View.VISIBLE
                    binding.tvTemp.visibility = View.VISIBLE
                }

                if (mainViewModel.checkHome == null) {
                    mainViewModel.checkHome = true
                    requestApiData()
                    requestWeatherApiData()
                } else if (mainViewModel.checkHome == false) {
                    mainViewModel.newsHomeResponse.value!!.data?.let { mAdapter.setData(it) }

                    if (mainViewModel.weatherResponse.value!!.data == null) {
                        requestWeatherApiData()
                    } else {
                        var dayWeather = mainViewModel.weatherResponse.value!!.data

                        if (dayWeather != null) {
                            val city = dayWeather.city
                            if (city.name == "Thành phố Hồ Chí Minh") {
                                binding.tvCity.text = "Hồ Chí Minh"
                            } else {
                                binding.tvCity.text = city.name
                            }

                            val day = dayWeather.list[0]
                            binding.tvTemp.text =
                                "${day.temp.tempDay.toInt()}°C / ${day.temp.tempMin.toInt()}°C - ${day.temp.tempMax.toInt()}°C"
                        }
                    }
                }
            }
        }

        mainViewModel.weatherResponse.observe(viewLifecycleOwner) { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    if (binding.tvCity.visibility == View.INVISIBLE) {
                        binding.tvCity.visibility = View.VISIBLE
                        binding.tvTemp.visibility = View.VISIBLE
                    }
                    val city = networkResult.data!!.city
                    if (city.name == "Thành phố Hồ Chí Minh") {
                        binding.tvCity.text = "Hồ Chí Minh"
                    } else {
                        binding.tvCity.text = city.name
                    }
                    val day = networkResult.data.list[0]
                    binding.imgIcon.load("https://openweathermap.org/img/wn/${day.weather[0].icon}@2x.png") {
                        crossfade(600)
                        error(R.drawable.ic_error_placeholder)
                    }

                    binding.tvTemp.text =
                        "${day.temp.tempDay.toInt()}°C / ${day.temp.tempMin.toInt()}°C - ${day.temp.tempMax.toInt()}°C"

                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading ->
                    Log.d("ducpt19", "loading")
            }
        }

        mainViewModel.newsHomeResponse.observe(viewLifecycleOwner) { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    networkResult.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    if (mainViewModel.checkHome == false) {
                        networkResult.data?.let { mAdapter.setData(it) }
                    } else {
                        loadDataFromCache()
                    }
                }
                is NetworkResult.Loading ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        }

        mainViewModel.isChangeProvince.observe(viewLifecycleOwner) {
            if (it) {
                requestWeatherApiData()
            }
        }

    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readNews.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].item)
                } else if (database.isNullOrEmpty()) {
                    binding.errorImageView.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun requestApiData() {
        mainViewModel.getHomeNews(Constants.Q_ENTERTAINMENT)
    }

    private fun requestWeatherApiData() {
        mainViewModel.getWeather(mainViewModel.getProvince())
    }

}