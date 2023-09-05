package com.example.news.ui.fragment.video

import android.net.Uri
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.MediaController
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.adapter.ItemVideoAdapter
import com.example.news.base.BaseFragment
import com.example.news.databinding.FragmentPlayVideoBinding
import com.example.news.models.Daum
import com.example.news.utils.NetworkResult
import com.example.news.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class PlayVideoFragment : BaseFragment<FragmentPlayVideoBinding>(FragmentPlayVideoBinding::inflate) {

    private val mAdapter by lazy { ItemVideoAdapter()}
    private val mainViewModel: MainViewModel by activityViewModels()
    private val args: PlayVideoFragmentArgs by navArgs()
    private var count = 0

    private lateinit var currentDaum: Daum

    override fun initView() {
        currentDaum = args.video!!
        binding.video = currentDaum

        playVideo()
        binding.linearcontainer.setOnClickListener {
            if(count == 0){
                binding.imgDetail.startAnimation(AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.rotate_clockwise
                ))
                binding.imgDetail.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)

                binding.tvDes.visibility = View.VISIBLE
                count = 1
            }else
            {
                binding.imgDetail.startAnimation(AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.rotate_anticlockwise
                ))
                binding.imgDetail.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                binding.tvDes.visibility = View.GONE
                count = 0
            }

        }
        mainViewModel.videosResponse.value!!.data?.let { mAdapter.setData(it.data) }
        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.rvVideo.adapter = mAdapter
        binding.rvVideo.layoutManager = LinearLayoutManager(requireContext())

    }
    private fun playVideo(){
        val uri: Uri = Uri.parse(currentDaum.preview.video.source.n720)
        binding.videoView.setVideoURI(uri)
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.requestFocus()
        binding.videoView.start()
    }
//
    override fun obServerLivedata() {
        mainViewModel.videosResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.setData(it.data) }
                }
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {

                }
            }
        }
    }



}