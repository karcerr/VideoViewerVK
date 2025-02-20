package com.videoviewervk.domain

import javax.inject.Inject

class VideoApiUseCase @Inject constructor(
    private val repository: VideoApiRepository
) {
    suspend fun getVideos(): List<Video> {
        return repository.getVideos()
    }
}