package com.videoviewervk.presentation.feature.video_player

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.videoviewervk.presentation.util.NetworkFetchEvent

class VideoPlayerFragmentViewModel: ViewModel() {
    var videoPosition: Long = 0L
    private var exoPlayer: ExoPlayer? = null
    private val _networkError = MutableLiveData<NetworkFetchEvent<String>>()
    val networkError: LiveData<NetworkFetchEvent<String>> get() = _networkError

    fun initializePlayer(context: Context, videoUrl: String): ExoPlayer {
        Log.d("videoPlayerFragment", "Video at $videoPosition, url is $videoUrl")

        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(context).build().apply {
                val mediaItem = MediaItem.fromUri(Uri.parse(videoUrl))
                setMediaItem(mediaItem)
                prepare()
                seekTo(videoPosition)
                playWhenReady = true

                addListener(object: Player.Listener {
                    override fun onPlayerError(error: PlaybackException) {
                        if (error.cause is java.net.UnknownHostException)
                            _networkError.postValue(NetworkFetchEvent("Network Error"))
                    }
                })
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