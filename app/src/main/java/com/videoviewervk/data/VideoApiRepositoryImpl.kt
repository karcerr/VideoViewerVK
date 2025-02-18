package com.videoviewervk.data

import android.content.Context
import com.videoviewervk.domain.Video
import com.videoviewervk.domain.VideoApiRepository
import javax.inject.Inject

class VideoApiRepositoryImpl @Inject constructor(
    context: Context
): VideoApiRepository {
    override suspend fun getVideos(): List<Video> {
        TODO("Not yet implemented")
    }
}