package com.videoviewervk.di

import com.videoviewervk.data.VideoApiRepositoryImpl
import com.videoviewervk.domain.VideoApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    abstract fun bindVideoApiRepository(
        videoApiRepositoryImpl: VideoApiRepositoryImpl
    ): VideoApiRepository
}