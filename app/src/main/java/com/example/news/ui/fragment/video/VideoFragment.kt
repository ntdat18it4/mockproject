package com.example.news.ui.fragment.video

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.adapter.VideoAdapter
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentVideoBinding
import com.example.news.utils.NetworkResult
import com.example.news.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class VideoFragment : BaseFragment<FragmentVideoBinding>(FragmentVideoBinding::inflate) {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val mAdapter by lazy { VideoAdapter{
        if(mainViewModel.checkInternet.value == true){
            val action = VideoFragmentDirections.actionVideoFragmentToPlayVideoFragment(it)
            findNavController().navigate(action)
        }else{
            showSnackBar("No internet connection..")
        }
    } }

    override fun initView() {
        requestApiData()
        setupRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            requestApiData()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setupRecyclerView() {
        binding.rvVideo.adapter = mAdapter
        binding.rvVideo.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun obServerLivedata() {

        mainViewModel.checkInternet.observe(viewLifecycleOwner){ checkInternet ->
            if(!checkInternet){
                if(mainViewModel.videosResponse.value?.data == null ){
                    binding.errorImageView.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.VISIBLE
                }else{
                    mainViewModel.videosResponse.value?.data?.let { mAdapter.setData(it) }
                }
            }else{
                if(mainViewModel.videosResponse.value?.data == null){
                    if(binding.errorImageView.visibility == View.VISIBLE){
                        binding.errorImageView.visibility = View.INVISIBLE
                        binding.errorTextView.visibility = View.INVISIBLE
                    }
                    requestApiData()
                }else{
                    mainViewModel.videosResponse.value?.data?.let { mAdapter.setData(it) }
                }
            }
        }

        mainViewModel.videosResponse.observe(viewLifecycleOwner) { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    networkResult.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    if (mainViewModel.checkVideo == false) {
                        networkResult.data?.let { mAdapter.setData(it) }
                    }
                }
                is NetworkResult.Loading ->
                {

                }
            }
        }
    }

    private fun requestApiData(){
        mainViewModel.getVideos()
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}
            .show()
    }


}