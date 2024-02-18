package com.example.dp.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dp.databinding.ActivityAddSongsToSetBinding
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper


class AddSongsToSetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSongsToSetBinding
    private lateinit var set:com.example.dp.objects.Set
    private lateinit var tabsList: ArrayList<Song>
    private val dbHelper = TabsDBHelper(this)
    private val selectedSongsNames = arrayListOf<String>()
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private lateinit var tabsListView: ListView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSongsToSetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        set = com.example.dp.objects.Set(

            intent.getIntExtra("EXTRA_SET_ID", 0),
            intent.getStringExtra("EXTRA_SET_NAME").toString()
        )

        createActivityViews()

        binding.addSongsBtn.setOnClickListener {
            addSelectedSongs()
        }
    }


    private fun createActivityViews(){

        binding.setTittle.text = set.setName

        tabsList = dbHelper.allTabs

        val tabsListName = arrayListOf<String>()

        for (i in 0 until  tabsList.size){
            tabsListName.add(tabsList[i].songName)
        }

        tabsListView = binding.songsListView
        arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_multiple_choice, tabsListName)

        tabsListView.adapter = arrayAdapter

    }

     private fun addSelectedSongs(){

         val selectedSongs = arrayListOf<Song>()

            for (i in 0 until arrayAdapter.count){

                if (tabsListView.isItemChecked(i)){
                    val song = arrayAdapter.getItem(i)
                    if (song != null) {
                        selectedSongsNames.add(song)
                    }
                }
            }

             for (selectedSongName in selectedSongsNames){

                 for (song in dbHelper.allTabs){
                     if (selectedSongName == song.songName){
                         selectedSongs.add(song)
                     }
                 }
             }

             for (selectedSong in selectedSongs){
                 dbHelper.addSongToSet(selectedSong, set)
             }
    }


}
































