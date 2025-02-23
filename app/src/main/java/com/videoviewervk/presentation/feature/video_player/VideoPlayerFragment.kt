package com.videoviewervk.presentation.feature.video_player

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.videoviewervk.R
import com.videoviewervk.databinding.FragmentVideoPlayerBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoPlayerFragment: Fragment(R.layout.fragment_video_player) {
    private val viewModel: VideoPlayerFragmentViewModel by viewModels()
    private lateinit var binding: FragmentVideoPlayerBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVideoPlayerBinding.bind(view)

        val args = VideoPlayerFragmentArgs.fromBundle(requireArguments())
        val videoUrl = args.videoUrl
        binding.playerView.player = viewModel.initializePlayer(requireContext(), videoUrl)

        viewModel.networkError.observe(this) {
            it.getContentIfNotHandled()?.let { content ->
                Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.releasePlayer()
    }
}