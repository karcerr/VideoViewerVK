package com.videoviewervk.data

import android.content.Context
import com.videoviewervk.domain.Video
import com.videoviewervk.domain.VideoApiRepository
import javax.inject.Inject

class VideoApiRepositoryImpl @Inject constructor(
    private val api: VideoApi
): VideoApiRepository {
    override suspend fun getVideos(): List<Video> {
        return api.getVideos()
    }
}