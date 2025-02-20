package com.videoviewervk.data

import com.videoviewervk.domain.Video
import retrofit2.http.GET

interface VideoApi {
    @GET("ca84582cbeb4fc123a13290a586da925/raw/14a27bd0bcd0cd323b35ad79cf3b493dddf6216b/videos.json")
    suspend fun getVideos(): List<Video>
}