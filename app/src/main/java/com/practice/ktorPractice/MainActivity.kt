package com.practice.ktorPractice

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.practice.ktorPractice.databinding.ActivityMainBinding
import com.practice.network.collectResponseState
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this)[MainActivityVM::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listenObserver()
        getFacts()
    }

    private fun listenObserver() = lifecycleScope.launch {
        viewModel.getFactsResponse.collectResponseState(
            isLoading = {
                binding.progress.isVisible = true
            },
            isSuccess = {
                binding.progress.isVisible = false
                binding.tvId.text = this?.userId.toString()
                binding.tvUser.text = this?.title.orEmpty()
            },
            isError = {
                binding.progress.isVisible = false
                Snackbar.make(binding.root, this, Snackbar.LENGTH_LONG).show()
            }
        )
    }

    private fun getFacts() {
        viewModel.getFacts()
    }
}