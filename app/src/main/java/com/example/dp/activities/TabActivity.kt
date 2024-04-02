package com.example.dp.activities


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dp.R
import com.example.dp.databinding.ActivityTabBinding
import com.example.dp.objects.ChordsFinder
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper
import com.example.dp.objects.ViewPagerAdapter


class TabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding
    private lateinit var tab: Song
    private var dbHelper = TabsDBHelper(this)


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val tabs = dbHelper.allTabs

        tab = tabs[0]

        val viewPagerAdapter = ViewPagerAdapter(tabs)

        val viewPager = binding.tabsViewPager
        viewPager.adapter = viewPagerAdapter



        val toolbar:androidx.appcompat.widget.Toolbar = binding.tabScreenToolbar
        setSupportActionBar(toolbar)


    }




}

