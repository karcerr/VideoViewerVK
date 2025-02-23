package com.videoviewervk
import com.videoviewervk.domain.Video
import com.videoviewervk.domain.VideoApiRepository
import com.videoviewervk.domain.VideoApiUseCase
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class VideoApiUseCaseTest {
    private lateinit var useCase: VideoApiUseCase
    private val mockRepository = mockk<VideoApiRepository>()

    @Before
    fun setup() {
        useCase = VideoApiUseCase(mockRepository)
    }

    @Test
    fun `getVideos should return video list from repository`() = runTest {
        val expectedVideos = listOf(Video(1, "Test", "url", "duration", "videoUrl"))
        coEvery { mockRepository.getVideos() } returns expectedVideos

        val result = useCase.getVideos()

        assertEquals(expectedVideos, result)
    }
}