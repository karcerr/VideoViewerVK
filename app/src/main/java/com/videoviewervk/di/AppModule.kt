package com.videoviewervk.di

import android.content.Context
import com.videoviewervk.domain.VideoApiRepository
import com.videoviewervk.domain.VideoApiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Provides
    fun provideVideoApiUseCase(
        videoApiRepository: VideoApiRepository
    ): VideoApiUseCase {
        return VideoApiUseCase(videoApiRepository)
    }
}