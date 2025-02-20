package com.videoviewervk.di

import android.content.Context
import com.videoviewervk.data.VideoApi
import com.videoviewervk.domain.VideoApiRepository
import com.videoviewervk.domain.VideoApiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
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
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/poudyalanil/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideVideoApi(retrofit: Retrofit): VideoApi {
        return retrofit.create(VideoApi::class.java)
    }
}
