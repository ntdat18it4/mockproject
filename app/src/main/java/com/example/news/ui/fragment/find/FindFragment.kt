package com.example.news.ui.fragment.find

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.adapter.FavoriteArticleAdapter
import com.example.news.adapter.FindArticleAdapter
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentFavoriteArticleBinding
import com.example.news.databinding.FragmentFindBinding
import com.example.news.utils.NetworkResult
import com.example.news.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class FindFragment :
    BaseFragment<FragmentFindBinding>(FragmentFindBinding::inflate),
    SearchView.OnQueryTextListener {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val mAdapter: FindArticleAdapter by lazy {
        FindArticleAdapter()
    }

    override fun initView() {
        val activity: AppCompatActivity = this.activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        activity.supportActionBar?.title = ""
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.find_menu, menu)
                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@FindFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                if (menuItem.itemId == android.R.id.home) {
                    val action = FindFragmentDirections.actionFindFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
              return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        setupRecyclerView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun searchApiData(searchQuery: String) {

        mainViewModel.searchNews(searchQuery)
        mainViewModel.searchedNewsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    val news = response.data
                    news?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvFind.adapter = mAdapter
        binding.rvFind.layoutManager = LinearLayoutManager(requireContext())
    }

}