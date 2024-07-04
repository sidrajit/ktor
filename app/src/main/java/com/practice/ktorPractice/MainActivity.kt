package com.practice.ktorPractice

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.practice.ktorPractice.databinding.ActivityMainBinding
import com.practice.network.Network
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val ktorClient = Network()
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

    private fun listenObserver() {
        lifecycleScope.launch {
            ktorClient.response.collectLatest { data ->
                Log.d("", "listenObserver: $data")
                binding.tvId.text = data?._id.orEmpty()
                binding.tvUser.text = data?.user.orEmpty()
            }
        }
    }

    private fun getFacts() {
        CoroutineScope(Dispatchers.IO).launch {
            ktorClient.getFacts()
        }
    }
}