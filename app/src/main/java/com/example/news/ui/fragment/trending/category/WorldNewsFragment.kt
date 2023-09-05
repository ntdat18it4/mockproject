package com.example.news.ui.fragment.trending.category

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.adapter.ItemTrendingNewsAdapter
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentTrendingPagesBinding
import com.example.news.utils.Constants
import com.example.news.utils.NetworkResult
import com.example.news.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorldNewsFragment : BaseFragment<FragmentTrendingPagesBinding>(
    FragmentTrendingPagesBinding::inflate
) {

    private val mAdapter by lazy { ItemTrendingNewsAdapter("Thế Giới") }
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun initView() {
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        setupRecyclerView()

        binding.swipeRefreshLayout.setOnRefreshListener {
            requestApiData()
            binding.swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun setupRecyclerView() {
        binding.rvHome.adapter = mAdapter
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun obServerLivedata() {
        mainViewModel.checkInternet.observe(viewLifecycleOwner) { checkInternet ->
            if (!checkInternet) {
                if (mainViewModel.checkWorld == null) {
                    binding.errorImageView.visibility = View.VISIBLE
                    binding.errorTextView.visibility = View.VISIBLE
                } else {
                    mainViewModel.checkWorld = false
                }
            } else {
                if (mainViewModel.checkWorld == null) {
                    mainViewModel.checkWorld = true
                    if (binding.errorImageView.visibility == View.VISIBLE) {
                        binding.errorImageView.visibility = View.INVISIBLE
                        binding.errorTextView.visibility = View.INVISIBLE
                    }
                    requestApiData()
                }else{
                    mainViewModel.newsWorldResponse.value?.data?.let { mAdapter.setData(it) }
                }
            }
        }

        mainViewModel.newsWorldResponse.observe(viewLifecycleOwner) { networkResult ->
            when (networkResult) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    networkResult.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    if (mainViewModel.checkWorld == false) {
                        networkResult.data?.let { mAdapter.setData(it) }
                    }
                }
                is NetworkResult.Loading ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun requestApiData() {
        mainViewModel.getWorldNews(Constants.Q_WORLD)
    }

}