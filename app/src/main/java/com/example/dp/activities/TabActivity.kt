package com.example.dp.activities

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dp.databinding.ActivityTabBinding
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper
import com.example.dp.objects.ViewPagerAdapter
import java.util.ArrayList


class TabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val tabs = intent.getSerializableExtra("EXTRA_SONGS") as java.util.ArrayList<Song>
        val tabID = intent.getIntExtra("EXTRA_SONG_ID", 0)
        var tabPosition = 0

        tabPosition = findTabPosition(tabs, tabID, tabPosition)


        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, tabs)
        val viewPager = binding.tabsViewPager
        viewPager.adapter = viewPagerAdapter

        //selecting the clicked tab as the starting tab of the viewPager, otherwise the view
        //pager always start by the first tab of the list
        if (tabPosition >= 0){viewPager.currentItem = tabPosition}

    }

    //finding the position of the tab by tab id
    private fun findTabPosition(
        tabs: ArrayList<Song>,
        tabID: Int,
        tabPosition: Int
    ): Int {
        var tabPosition1 = tabPosition
        for (i in 0 until tabs.size) {
            if (tabs[i].id == tabID) {
                tabPosition1 = i
            }
        }
        return tabPosition1
    }
}

