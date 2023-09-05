package com.example.news.ui.fragment.covid.category

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentCovidWorldBinding
import com.example.news.utils.NetworkResult
import com.example.news.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CovidWorldFragment : BaseFragment<FragmentCovidWorldBinding>(
    FragmentCovidWorldBinding::inflate
) {

    private val mainViewModel: MainViewModel by activityViewModels()

    @SuppressLint("SetTextI18n")
    override fun obServerLivedata() {
        mainViewModel.covidResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {
                    binding.totalCases.text = response.data?.worldTotal?.cases
                    binding.totalDeaths.text = response.data?.worldTotal?.deaths
                    binding.totalRecovered.text = response.data?.worldTotal?.totalRecovered
                    binding.totalNewCases.text = response.data?.worldTotal?.newCases
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {
                    binding.totalCases.text = "Loading..."
                    binding.totalDeaths.text = "Loading..."
                    binding.totalRecovered.text = "Loading..."
                    binding.totalNewCases.text = "Loading..."
                }
            }
        }

        mainViewModel.checkInternet.observe(viewLifecycleOwner){ checkInternet ->
            if(!checkInternet){
                if(mainViewModel.covidResponse.value?.data == null){
                    binding.totalCases.text = "Error..."
                    binding.totalDeaths.text = "Error..."
                    binding.totalRecovered.text = "Error..."
                    binding.totalNewCases.text = "Error..."
                }else{
                    binding.totalCases.text = mainViewModel.covidResponse.value?.data?.worldTotal?.cases
                    binding.totalDeaths.text = mainViewModel.covidResponse.value?.data?.worldTotal?.deaths
                    binding.totalRecovered.text = mainViewModel.covidResponse.value?.data?.worldTotal?.totalRecovered
                    binding.totalNewCases.text = mainViewModel.covidResponse.value?.data?.worldTotal?.newCases
                }
            }else {
                if(mainViewModel.covidResponse.value?.data != null){
                    binding.totalCases.text = mainViewModel.covidResponse.value?.data?.worldTotal?.cases
                    binding.totalDeaths.text = mainViewModel.covidResponse.value?.data?.worldTotal?.deaths
                    binding.totalRecovered.text = mainViewModel.covidResponse.value?.data?.worldTotal?.totalRecovered
                    binding.totalNewCases.text = mainViewModel.covidResponse.value?.data?.worldTotal?.newCases
                }else{
                    requestApiData()
                }
            }
        }
    }
    private fun requestApiData(){
        mainViewModel.getCovid()
    }
}