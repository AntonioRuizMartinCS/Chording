package com.example.dp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dp.R
import com.example.dp.adapters.TabsRVAdapter
import com.example.dp.databinding.ActivitySetBinding
import com.example.dp.models.TabsViewModel
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper

class SetActivity : AppCompatActivity(), TabsRVAdapter.OnItemClickListener, TabsRVAdapter.OnMenuItemClickListener {

    private lateinit var binding: ActivitySetBinding
    private lateinit var tabsForSet: ArrayList<Song>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val dbHelper = TabsDBHelper(this)
        val setID = intent.getIntExtra("EXTRA_SET_ID", 0)
        tabsForSet = dbHelper.getAllSongsForSet(setID)

        createActivityViews()


    }



    private fun createActivityViews(){

        val tabsRecyclerView = findViewById<RecyclerView>(R.id.songsInSetAV)

        tabsRecyclerView.layoutManager = LinearLayoutManager(this)

        val tabsData = ArrayList<TabsViewModel>()

        for (i in 0 until tabsForSet.size){
            tabsData.add(
                TabsViewModel(tabsForSet[i].songName, tabsForSet[i].artist, R.drawable.baseline_more_vert_24)
            )
        }


        val tabsAdapter = TabsRVAdapter(tabsData, this, this)

        tabsRecyclerView.adapter = tabsAdapter

    }

    override fun onItemClick(position: Int) {
        Intent(this, TabActivity::class.java).also {

            it.putExtra("EXTRA_SONG_ID", tabsForSet[position].id)
            it.putExtra("EXTRA_SONG_NAME", tabsForSet[position].songName)
            it.putExtra("EXTRA_SONG_ARTIST", tabsForSet[position].artist)
            it.putExtra("EXTRA_SONG_BODY", tabsForSet[position].songBody)
            it.putExtra("EXTRA_SONG_CAPO", tabsForSet[position].capo)
            it.putExtra("EXTRA_SONG_TUNING", tabsForSet[position].tuning)
            it.putExtra("EXTRA_SONG_KEY", tabsForSet[position].key)
            it.putExtra("EXTRA_SONG_CHORDS", tabsForSet[position].songChords)
            it.putExtra("EXTRA_MINUTES", tabsForSet[position].minutes)
            it.putExtra("EXTRA_SECONDS", tabsForSet[position].seconds)

            startActivity(it)
        }
    }

    override fun onDeleteClicked(position: Int) {


    }

    override fun onEditClicked(position: Int) {
        TODO("Not yet implemented")
    }
}