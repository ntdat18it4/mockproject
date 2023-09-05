package com.example.news.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news.ui.fragment.home.HomeFragment
import com.example.news.ui.fragment.trending.category.*
import com.example.news.utils.Constants



class PagerTrendingNewsAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return Constants.TOTAL_PAGE_NEWS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            Constants.PAGE_LATEST_NEWS -> LatestNewsFragment()
            Constants.PAGE_VIETNAM_NEWS -> VietNamNewsFragment()
            Constants.PAGE_WORLD_NEWS -> WorldNewsFragment()
            Constants.PAGE_ENTERTAINMENT_NEWS -> EntertainmentNewsFragment()
            Constants.PAGE_SPORT_NEWS -> SportNewsFragment()
            Constants.PAGE_SCIENCE_NEWS -> ScienceNewsFragment()
            Constants.PAGE_LAW_NEWS -> LawNewsFragment()
            Constants.PAGE_EDUCATION_NEWS -> EducationNewsFragment()
            Constants.PAGE_HEALTH_NEWS -> HealthNewsFragment()
            Constants.PAGE_LIFE_NEWS -> LifeNewsFragment()
            Constants.PAGE_TRAVEL_NEWS -> TravelNewsFragment()
            Constants.PAGE_IDEA_NEWS -> IdeaNewsFragment()
            Constants.PAGE_TALK_NEWS -> TalkNewsFragment()
            else -> LatestNewsFragment()
        }
    }
}