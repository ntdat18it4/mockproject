package com.example.news.ui.fragment.setting.bottomsheet

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.adapter.ProvinceAdapter
import com.example.news.base.BaseBottomSheetFragment
import com.example.news.databinding.ProvinceBottomSheetBinding
import com.example.news.utils.Constants
import com.example.news.viewmodel.MainViewModel

class ProvinceBottomSheet :
    BaseBottomSheetFragment<ProvinceBottomSheetBinding>(ProvinceBottomSheetBinding::inflate) {
    private val mainViewModel: MainViewModel by activityViewModels()

    private val mAdapter by lazy {
        ProvinceAdapter(Constants.listProvince) {
            mainViewModel.saveProvince(it)
            mainViewModel.isChangeProvince.value = true
            val action =
                ProvinceBottomSheetDirections.actionProvinceBottomSheetToSettingFragment()
            findNavController().navigate(action)
        }
    }

    override fun initView() {

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvLocation.adapter = mAdapter
        binding.rvLocation.layoutManager = LinearLayoutManager(requireContext())
    }
}