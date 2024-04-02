package com.example.dp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dp.databinding.ActivityAcknowledgementBinding
import com.example.dp.objects.TabsDBHelper


class AcknowledgementActivity : AppCompatActivity() {

    private var dbHelper = TabsDBHelper(this)

    private lateinit var binding: ActivityAcknowledgementBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAcknowledgementBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        createActivityViews()

    }

    private fun createActivityViews(){
        val acceptButton = binding.acceptButton
        val cancelButton = binding.cancelButton
        val checkBox = binding.copyrightCheckBox

        checkBox.isChecked = dbHelper.acknowledgement != 0

        acceptButton.setOnClickListener {

            dbHelper.updateAcknowledgement(checkBox.isChecked)

            finish() }


        cancelButton.setOnClickListener { finish() }

    }
}