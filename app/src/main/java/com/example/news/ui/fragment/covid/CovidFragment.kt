package com.example.news.ui.fragment.covid

import androidx.viewpager2.widget.ViewPager2
import com.example.news.R
import com.example.news.adapter.PagerCovidAdapter
import com.example.news.adapter.PagerCovidAdapter.Companion.PAGE_VIETNAM
import com.example.news.adapter.PagerCovidAdapter.Companion.PAGE_WORLD
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentCovidBinding
import com.example.news.utils.DepthPageTransformer

class CovidFragment : BaseFragment<FragmentCovidBinding>(FragmentCovidBinding::inflate) {

    private lateinit var pagerCovidAdapter: PagerCovidAdapter

    override fun initView() {
        setupViewpagerCovid()
        setupBottomNavigationCovid()
    }

    private fun setupViewpagerCovid() {
        binding.bottomPageCovid.itemIconTintList = null
        pagerCovidAdapter = PagerCovidAdapter(requireActivity())
        binding.vpCovid.adapter = pagerCovidAdapter
        binding.vpCovid.setPageTransformer(DepthPageTransformer())
        binding.vpCovid.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    PAGE_VIETNAM -> binding.bottomPageCovid.menu.findItem(R.id.bottomCovidVietnam).isChecked =
                        true
                    PAGE_WORLD -> binding.bottomPageCovid.menu.findItem(R.id.bottomCovidWorld).isChecked =
                        true
                }
            }
        })
    }

    private fun setupBottomNavigationCovid() {
        binding.bottomPageCovid.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottomCovidVietnam -> binding.vpCovid.currentItem = PAGE_VIETNAM
                R.id.bottomCovidWorld -> binding.vpCovid.currentItem = PAGE_WORLD
            }
            return@setOnItemSelectedListener true
        }
    }

}