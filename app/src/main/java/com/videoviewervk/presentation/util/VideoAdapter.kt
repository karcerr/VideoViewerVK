package com.videoviewervk.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.videoviewervk.R
import com.videoviewervk.databinding.VideoItemBinding
import com.videoviewervk.domain.Video
import eightbitlab.com.blurview.BlurView

class VideoAdapter (
    private val onItemClicked: (Video) -> Unit
): RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {
    private val items = mutableListOf<Video>()

    fun submitList(videoList: List<Video>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = items.size
            override fun getNewListSize() = videoList.size
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition].id == videoList[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return items[oldItemPosition] == videoList[newItemPosition]
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(videoList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = VideoItemBinding.inflate(inflater, parent, false)
        return VideoViewHolder(binding, onItemClicked)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(items[position])
    }
    class VideoViewHolder (
        private val binding: VideoItemBinding,
        private val onVideoClicked: (Video) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video) {
            binding.title.text = if(video.title.length >= 20) video.title.substring(0, 20) + "..." else video.title
            binding.duration.text = video.duration
            Glide.with(binding.root.context)
                .load(video.thumbnailUrl)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.coverImage)
            binding.root.setOnClickListener {
                onVideoClicked(video)
            }
            setupBlurView(binding.titleBlurView)
            setupBlurView(binding.durationBlurView)
        }
        private fun setupBlurView(blurView: BlurView) {
            val radius = 16f
            val rootView = binding.relativeLayout
            blurView.setupWith(rootView)
                .setBlurRadius(radius)
            blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
            blurView.clipToOutline = true
        }
    }
}

