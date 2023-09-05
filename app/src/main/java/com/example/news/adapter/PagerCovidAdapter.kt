package com.example.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news.ui.fragment.covid.category.CovidVietnamFragment
import com.example.news.ui.fragment.covid.category.CovidWorldFragment

class PagerCovidAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    companion object{
        const val TOTAL_PAGE = 2
        const val PAGE_VIETNAM = 0
        const val PAGE_WORLD = 1
    }

    override fun getItemCount(): Int {
        return TOTAL_PAGE
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_VIETNAM -> CovidVietnamFragment()
            PAGE_WORLD -> CovidWorldFragment()
            else -> CovidVietnamFragment()
        }
    }
}