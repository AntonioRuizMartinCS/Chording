package com.example.dp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dp.databinding.ActivityTestBinding

class ActivityTest : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.testReadFile.text = intent.getStringExtra("EXTRA_TEST").toString()

    }
}