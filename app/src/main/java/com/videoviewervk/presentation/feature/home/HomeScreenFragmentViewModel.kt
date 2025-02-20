package com.videoviewervk.presentation.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.videoviewervk.domain.Video
import com.videoviewervk.domain.VideoApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenFragmentViewModel @Inject constructor(
    private val useCase: VideoApiUseCase
): ViewModel() {
    private val _videoList = MutableLiveData<List<Video>>()
    val videoList: LiveData<List<Video>> get() = _videoList

    fun fetchVideos() {
        viewModelScope.launch {
            try {
                val result = useCase.getVideos()
                Log.d("fetchVideos", "${result}")
                _videoList.postValue(result)
            } catch (e: Exception) {
                //todo
            }
        }
    }
}