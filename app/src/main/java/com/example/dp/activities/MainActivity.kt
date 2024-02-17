package com.example.dp.activities


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dp.*
import com.example.dp.adapters.TabsRVAdapter
import com.example.dp.databinding.ActivityMainBinding
import com.example.dp.models.TabsViewModel
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper


class MainActivity : AppCompatActivity(), TabsRVAdapter.OnItemClickListener, TabsRVAdapter.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabsDBHelper: TabsDBHelper
    private lateinit var myTabsList:ArrayList<Song>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tabsDBHelper = TabsDBHelper(this)

//        tabsDBHelper.deleteTables()
        myTabsList = tabsDBHelper.allTabs

        createButtons()
        createTabsRecyclerView()
    }

    private fun createButtons(){

        val addBtn = binding.addBtn
        val setsBtn = binding.mySetsLabel
        val tabsBtn = binding.myTabsLabel
        val addSetBtn = binding.addSetButton

        addBtn.setOnClickListener{
            Intent(this, UploadActivity::class.java).also {
                startActivity(it)
            }
        }

        setsBtn.setOnClickListener{
            binding.setsRecyclerView.visibility = View.VISIBLE
            binding.tabsRecyclerView.visibility = View.INVISIBLE
            binding.addSetButton.visibility = View.VISIBLE
        }
        tabsBtn.setOnClickListener{
            binding.setsRecyclerView.visibility = View.INVISIBLE
            binding.tabsRecyclerView.visibility = View.VISIBLE
            binding.addSetButton.visibility = View.INVISIBLE
        }

        addSetBtn.setOnClickListener{
//            https://stackoverflow.com/questions/4134117/edittext-on-a-popup-window

            val alert: AlertDialog.Builder = AlertDialog.Builder(this)

            alert.setTitle("add set name")
            alert.setMessage("Message")

            // Set an EditText view to get user input

            // Set an EditText view to get user input
            val input = EditText(this)
            alert.setView(input)

            alert.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                // Do something with value!
                tabsDBHelper.addOneSet(input.text.toString())

            })

            alert.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { _, _ ->
                    // Canceled.
                })

            alert.show()


        }




    }

    private fun createTabsRecyclerView(){

        val tabsRecyclerView = findViewById<RecyclerView>(R.id.tabsRecyclerView)

        tabsRecyclerView.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<TabsViewModel>()

        for (i in 0 until myTabsList.size){
            data.add(
                TabsViewModel(myTabsList[i].songName, myTabsList[i].artist, R.drawable.baseline_more_vert_24)
            )
        }

        val adapter = TabsRVAdapter(data, this, this)

        tabsRecyclerView.adapter = adapter

    }





    override fun onItemClick(position: Int) {

        Intent(this, TabActivity::class.java).also {

            it.putExtra("EXTRA_SONG_ID", myTabsList[position].id)
            it.putExtra("EXTRA_SONG_NAME", myTabsList[position].songName)
            it.putExtra("EXTRA_SONG_ARTIST", myTabsList[position].artist)
            it.putExtra("EXTRA_SONG_BODY", myTabsList[position].songBody)
            it.putExtra("EXTRA_SONG_CAPO", myTabsList[position].capo)
            it.putExtra("EXTRA_SONG_TUNING", myTabsList[position].tuning)
            it.putExtra("EXTRA_SONG_KEY", myTabsList[position].key)
            it.putExtra("EXTRA_SONG_CHORDS", myTabsList[position].songChords)
            it.putExtra("EXTRA_MINUTES", myTabsList[position].minutes)
            it.putExtra("EXTRA_SECONDS", myTabsList[position].seconds)

            startActivity(it)
        }
    }

    override fun onDeleteClicked(position: Int) {

        tabsDBHelper.deleteOneTab(myTabsList[position])
        myTabsList.removeAt(position)
        createTabsRecyclerView()
    }

    override fun onEditClicked(position: Int) {
        Intent(this, EditTabActivity::class.java).also {

            it.putExtra("EXTRA_SONG_ID", myTabsList[position].id)
            it.putExtra("EXTRA_SONG_NAME", myTabsList[position].songName)
            it.putExtra("EXTRA_SONG_ARTIST", myTabsList[position].artist)
            it.putExtra("EXTRA_SONG_BODY", myTabsList[position].songBody)
            it.putExtra("EXTRA_SONG_CAPO", myTabsList[position].capo)
            it.putExtra("EXTRA_SONG_TUNING", myTabsList[position].tuning)
            it.putExtra("EXTRA_SONG_KEY", myTabsList[position].key)
            it.putExtra("EXTRA_SONG_CHORDS", myTabsList[position].songChords)
            it.putExtra("EXTRA_MINUTES", myTabsList[position].minutes)
            it.putExtra("EXTRA_SECONDS", myTabsList[position].seconds)

            startActivity(it)
        }
    }
}















