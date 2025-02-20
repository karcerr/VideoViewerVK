package com.videoviewervk.presentation.feature.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.videoviewervk.R
import com.videoviewervk.databinding.FragmentHomeBinding
import com.videoviewervk.presentation.util.VideoAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment: Fragment(R.layout.fragment_home) {
    private val viewModel: HomeScreenFragmentViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: VideoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        setUpObservers()
        setUpSwipeRefresh()
        if (viewModel.videoList.value.isNullOrEmpty()) {
            showShimmer()
            viewModel.fetchVideos()
        }
    }

    private fun setUpSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchVideos()
        }
    }

    private fun setupRecyclerView() {
        adapter = VideoAdapter { video ->
            val action = HomeScreenFragmentDirections
                .actionHomeScreenFragmentToVideoPlayerFragment(video.videoUrl)
            findNavController().navigate(action)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun setUpObservers() {
        viewModel.videoList.observe(this) { videos ->
            binding.swipeRefreshLayout.isRefreshing = false
            if (videos.isEmpty())
                showShimmer()
            else {
                adapter.submitList(videos)
                hideShimmer()
            }
        }
    }
    private fun showShimmer() {
        binding.shimmerLayout.startShimmer()
        binding.recyclerView.visibility = View.GONE
        binding.shimmerLayout.visibility = View.VISIBLE
    }
    private fun hideShimmer() {
        binding.shimmerLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.shimmerLayout.stopShimmer()
    }
}