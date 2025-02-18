package com.videoviewervk.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.videoviewervk.R
import com.videoviewervk.databinding.ActivityMainBinding
import com.videoviewervk.presentation.util.VideoAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: VideoAdapter
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureEdgeToEdge()
        showShimmer()
        setupRecyclerView()
        setUpObservers()
    }

    private fun configureEdgeToEdge() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = VideoAdapter { video ->
            openVideoUrl(video.videoUrl)
        }
        binding.recyclerView.adapter = adapter
    }
    private fun openVideoUrl(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open URL", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setUpObservers() {
        viewModel.videoList.observe(this) { videos ->
            adapter.submitList(videos)
            if (videos.isEmpty())
                showShimmer()
            else
                hideShimmer()
        }
    }
    private fun showShimmer() {
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE
    }
    private fun hideShimmer() {
        binding.shimmerLayout.visibility = View.GONE
        binding.shimmerLayout.stopShimmer()
    }
}