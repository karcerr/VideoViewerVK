package com.videoviewervk.presentation.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.videoviewervk.domain.Video
import com.videoviewervk.domain.VideoApiUseCase
import com.videoviewervk.presentation.util.NetworkFetchEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenFragmentViewModel @Inject constructor(
    private val useCase: VideoApiUseCase
): ViewModel() {
    private val _videoList = MutableLiveData<List<Video>>()
    val videoList: LiveData<List<Video>> get() = _videoList

    private val _networkError = MutableLiveData<NetworkFetchEvent<Boolean>>()
    val networkError: LiveData<NetworkFetchEvent<Boolean>> get() = _networkError

    fun fetchVideos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = useCase.getVideos()
                Log.d("fetchVideos", "$result")
                _videoList.postValue(result)
            }  catch (e: Exception) {
                when (e) {
                    is java.net.UnknownHostException, // No internet
                    is java.net.SocketTimeoutException -> { // Request timeout
                        _videoList.postValue(listOf())
                        _networkError.postValue(NetworkFetchEvent(true))
                    }
                    else -> throw e
                }
            }
        }
    }
}