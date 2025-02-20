package com.videoviewervk.presentation.feature.video_player

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class VideoPlayerFragmentViewModel: ViewModel() {
    private var videoPosition: Long = 0L
    private var exoPlayer: ExoPlayer? = null

    fun initializePlayer(context: Context, videoUrl: String): ExoPlayer {
        Log.d("videoPlayerFragment", "Video at $videoPosition, url is $videoUrl")

        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
                setMediaItem(mediaItem)
                prepare()
                seekTo(videoPosition)
                playWhenReady = true
            }
        }
        return exoPlayer!!
    }
    fun releasePlayer() {
        videoPosition = exoPlayer?.currentPosition ?: 0
        exoPlayer?.release()
        exoPlayer = null
    }
}