package com.example.dp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dp.R
import com.example.dp.adapters.TabsRVAdapter
import com.example.dp.databinding.ActivitySetBinding
import com.example.dp.models.TabsViewModel
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper
import kotlin.properties.Delegates

class SetActivity : AppCompatActivity(), TabsRVAdapter.OnItemClickListener, TabsRVAdapter.OnMenuItemClickListener {

    private lateinit var binding: ActivitySetBinding
    private lateinit var tabsForSet: ArrayList<Song>
    private lateinit var dbHelper: TabsDBHelper
    private lateinit var setName:String
    private var setID by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbHelper = TabsDBHelper(this)
        setID = intent.getIntExtra("EXTRA_SET_ID", 0)
        setName = intent.getStringExtra("EXTRA_SET_NAME").toString()
        tabsForSet = dbHelper.getAllSongsForSet(setID)

        createActivityViews()


    }



    private fun createActivityViews(){


        //creating recycler view
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

        //create tittle text view
        binding.setName.text = setName

        binding.addSongsBtn.setOnClickListener {
            Intent(this, AddSongsToSetActivity::class.java).also {
                it.putExtra("EXTRA_SET_ID", setID)
                it.putExtra("EXTRA_SET_NAME", setName)
                startActivity(it)
            }

        }

    }

    override fun onItemClick(position: Int) {
        Intent(this, TabActivity::class.java).also {

            it.putExtra("EXTRA_SONGS", tabsForSet)
            it.putExtra("EXTRA_SONG_ID", tabsForSet[position].id)

            startActivity(it)
        }
    }

    override fun onDeleteClicked(position: Int) {

        dbHelper.deleteRelationship(tabsForSet[position].id)

        tabsForSet.removeAt(position)

        createActivityViews()



    }

    override fun onEditClicked(position: Int) {

    }
}