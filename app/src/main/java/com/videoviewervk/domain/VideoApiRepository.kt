package com.videoviewervk.domain


interface VideoApiRepository {
    suspend fun getVideos(): List<Video>
}