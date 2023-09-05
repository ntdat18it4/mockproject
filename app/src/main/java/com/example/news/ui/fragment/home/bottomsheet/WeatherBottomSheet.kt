package com.example.news.ui.fragment.home.bottomsheet

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.news.R
import com.example.news.adapter.ItemWeatherAdapter
import com.example.news.base.BaseBottomSheetFragment
import com.example.news.databinding.WeatherBottomSheetBinding
import com.example.news.viewmodel.MainViewModel


class WeatherBottomSheet :
    BaseBottomSheetFragment<WeatherBottomSheetBinding>(WeatherBottomSheetBinding::inflate) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val mAdapter by lazy { ItemWeatherAdapter() }

    override fun initView() {
        val weather = mainViewModel.weatherResponse.value!!.data
        val city = weather!!.city
        binding.imgWF.load("https://openweathermap.org/img/wn/${weather.list[0].weather[0].icon}@2x.png"){
            crossfade(600)
            error(R.drawable.ic_error_placeholder)
        }

        binding.tvCity.text = city.name
        binding.tempCurrent.text = weather.list[0].temp.tempDay.toInt().toString()

        mAdapter.setData(weather)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvDailyWeather.adapter = mAdapter
        binding.rvDailyWeather.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
    }

}