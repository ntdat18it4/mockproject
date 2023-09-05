package com.example.news.ui.fragment.favorites

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.news.adapter.FavoriteArticleAdapter
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentFavoriteArticleBinding
import com.example.news.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteArticleBinding>(FragmentFavoriteArticleBinding::inflate) {

    private val mainViewModel: MainViewModel by viewModels()
    private val mAdapter : FavoriteArticleAdapter by lazy {
        FavoriteArticleAdapter(requireActivity(),mainViewModel)
    }
    override fun initView() {

        setupRecyclerView(binding.favoriteArticleRecyclerView)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun obServerLivedata() {
        mainViewModel.readFavoriteArticles.observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()) {
                    binding.noDataImageView.visibility = View.VISIBLE
                    binding.noDataTextView.visibility = View.VISIBLE
                binding.favoriteArticleRecyclerView.visibility = View.INVISIBLE
            } else {
                if(binding.favoriteArticleRecyclerView.visibility == View.INVISIBLE){
                    binding.favoriteArticleRecyclerView.visibility = View.VISIBLE
                }
                binding.noDataImageView.visibility = View.INVISIBLE
                binding.noDataTextView.visibility = View.INVISIBLE
                mAdapter.setData(it)
            }
        }
    }

    override fun initData() {

    }

}