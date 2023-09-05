package com.example.news.ui.fragment.trending

import com.example.news.adapter.PagerTrendingNewsAdapter
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentTrendingBinding
import com.example.news.utils.Constants
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingFragment : BaseFragment<FragmentTrendingBinding>(FragmentTrendingBinding::inflate){

    private val tabs = arrayListOf(
        Constants.LATEST_NEWS,
        Constants.VIETNAM,
        Constants.WORLD,
        Constants.ENTERTAINMENT,
        Constants.SPORT,
        Constants.SCIENCE,
        Constants.LAW,
        Constants.EDUCATION,
        Constants.HEALTH,
        Constants.LIFE,
        Constants.TRAVEL,
        Constants.IDEA,
        Constants.TALK
    )

    override fun initView() {
        setupTabLayout()
    }

    private fun setupTabLayout() {
        val pagerMainNewsAdapter = PagerTrendingNewsAdapter(requireActivity())
        binding.vpTrendingNews.adapter = pagerMainNewsAdapter
        binding.vpTrendingNews.offscreenPageLimit = 10
        binding.vpTrendingNews.currentItem = Constants.PAGE_LATEST_NEWS
        binding.vpTrendingNews.isUserInputEnabled = false
        TabLayoutMediator(binding.tabTrendingNews, binding.vpTrendingNews) { tab, index ->
            tab.text = tabs[index]
        }.attach()
    }

}