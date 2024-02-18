package com.example.dp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dp.databinding.ActivityTestBinding
import com.example.dp.objects.TabsDBHelper

class ActivityTest : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dbHelper = TabsDBHelper(this)
        val setID = intent.getIntExtra("EXTRA_SET_ID", 0)

        binding.testReadFile.text = dbHelper.getAllSongsForSet(setID).joinToString("\n")

    }
}