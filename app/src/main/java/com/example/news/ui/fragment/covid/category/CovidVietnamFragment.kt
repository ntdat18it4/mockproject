package com.example.news.ui.fragment.covid.category

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentCovidVietnamBinding
import com.example.news.utils.NetworkResult
import com.example.news.viewmodel.MainViewModel


class CovidVietnamFragment : BaseFragment<FragmentCovidVietnamBinding>(
    FragmentCovidVietnamBinding::inflate
) {

    private val mainViewModel: MainViewModel by activityViewModels()



    override fun initView() {
    }

    @SuppressLint("SetTextI18n")
    override fun obServerLivedata() {

        mainViewModel.covidResponse.observe(viewLifecycleOwner) { response ->
            when(response){
                is NetworkResult.Success -> {
                    if(!mainViewModel.checkCovid){
                        for (element in response.data!!.countries) {
                            if (element.countryName == "Vietnam") {
                                binding.totalCases.text = element.cases
                                binding.totalDeaths.text = element.deaths
                                binding.totalRecovered.text = element.totalRecovered
                                binding.totalNewCases.text = element.newCases
                                break
                            }
                        }
                    }
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
                    for (element in mainViewModel.covidResponse.value?.data!!.countries) {
                        if (element.countryName == "Vietnam") {
                            binding.totalCases.text = element.cases
                            binding.totalDeaths.text = element.deaths
                            binding.totalRecovered.text = element.totalRecovered
                            binding.totalNewCases.text = element.newCases
                            break
                        }
                    }
                }
            }else if(checkInternet){
                if(mainViewModel.covidResponse.value?.data == null){
                    requestApiData()
                }else{
                    for (element in mainViewModel.covidResponse.value?.data!!.countries) {
                        if (element.countryName == "Vietnam") {
                            binding.totalCases.text = element.cases
                            binding.totalDeaths.text = element.deaths
                            binding.totalRecovered.text = element.totalRecovered
                            binding.totalNewCases.text = element.newCases
                            break
                        }
                    }
                }
            }
        }
    }

    private fun requestApiData(){
        mainViewModel.getCovid()
    }
}