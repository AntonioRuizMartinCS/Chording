package com.example.dp.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.dp.databinding.ActivitySettingsBinding

@SuppressLint("UseSwitchCompatOrMaterialCode")
class SettingsActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySettingsBinding
    private lateinit var darkModeSwitch:Switch
    private var isDarkModeOn = false
    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        createActivityViews(sharedPreferences)
        getSharedPrefs(sharedPreferences)


    }


    private fun applyDayNightMode(isNightMode: Boolean) {
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun createActivityViews(sharedPreferences: SharedPreferences) {

        val copyrightBtn = binding.copyrightScreenBtn
        val aboutBtn = binding.aboutBtn

        copyrightBtn.setOnClickListener {

                   Intent(this, AcknowledgementActivity::class.java).also {
                       startActivity(it)
                   }
        }


        val editor = sharedPreferences.edit()

        darkModeSwitch = binding.darkModeSwitch

        darkModeSwitch.setOnCheckedChangeListener { _, _ ->

            isDarkModeOn = darkModeSwitch.isChecked
            applyDayNightMode(isDarkModeOn)
            editor.apply {

                putBoolean("darkMode", isDarkModeOn)
                apply()
            }

        }

        aboutBtn.setOnClickListener {

            Intent(this, AboutActivity::class.java).also {
                startActivity(it)
            }
        }

    }

    private fun getSharedPrefs(sharedPreferences: SharedPreferences){

        val darkMode = sharedPreferences.getBoolean("darkMode", false)

        darkModeSwitch.isChecked = darkMode

    }

}




















