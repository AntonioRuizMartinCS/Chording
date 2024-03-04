package com.example.dp.activities


import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.media.MicrophoneInfo.Coordinate3F
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dp.*
import com.example.dp.adapters.SetsRVAdapter
import com.example.dp.adapters.TabsRVAdapter
import com.example.dp.databinding.ActivityMainBinding
import com.example.dp.models.SetsViewModel
import com.example.dp.models.TabsViewModel
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper


class MainActivity : AppCompatActivity(), TabsRVAdapter.OnItemClickListener, TabsRVAdapter.OnMenuItemClickListener, SetsRVAdapter.OnItemClickListener, SetsRVAdapter.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tabsDBHelper: TabsDBHelper
    private lateinit var myTabsList:ArrayList<Song>
    private lateinit var mySetsList:ArrayList<com.example.dp.objects.Set>
    private lateinit var animator: ObjectAnimator




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //launch test activity
//        Intent(this, ActivityTest::class.java).also {
//            startActivity(it)
//        }



        tabsDBHelper = TabsDBHelper(this)

//        tabsDBHelper.deleteTables()
        myTabsList = tabsDBHelper.allTabs
        mySetsList = tabsDBHelper.allSets

        createRecyclersView()
        createButtons()
        createTransition(binding.myTabsLabel)
    }

    private fun createTransition(view: TextView){

        val transition = view.background as? TransitionDrawable
        transition?.startTransition(500)
    }

    private fun resetTransition(view: TextView){
        val transitionDrawable = ContextCompat.getDrawable(this, R.drawable.bottom_line_transition)
        view.background = transitionDrawable
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
            createTransition(setsBtn)
            resetTransition(tabsBtn)




        }
        tabsBtn.setOnClickListener{

            binding.setsRecyclerView.visibility = View.INVISIBLE
            binding.tabsRecyclerView.visibility = View.VISIBLE
            binding.addSetButton.visibility = View.INVISIBLE
            createTransition(tabsBtn)
            resetTransition(setsBtn)

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

                val setID = mySetsList.size + 1
                val setName = input.text.toString()

                val newSet = com.example.dp.objects.Set(setID,setName)
                tabsDBHelper.addOneSet(newSet)

                mySetsList = tabsDBHelper.allSets
                createRecyclersView()

            })

            alert.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { _, _ ->
                    // Canceled.
                })

            alert.show()


        }
    }

    private fun createRecyclersView(){

        val tabsRecyclerView = findViewById<RecyclerView>(R.id.tabsRecyclerView)
        val setsRecyclerView = findViewById<RecyclerView>(R.id.setsRecyclerView)

        tabsRecyclerView.layoutManager = LinearLayoutManager(this)
        setsRecyclerView.layoutManager = LinearLayoutManager(this)

        val tabsData = ArrayList<TabsViewModel>()
        val setsData = ArrayList<SetsViewModel>()

        for (i in 0 until myTabsList.size){
            tabsData.add(
                TabsViewModel(myTabsList[i].songName, myTabsList[i].artist, R.drawable.baseline_more_vert_24)
            )
        }

        for (i in 0 until mySetsList.size){
            setsData.add(
                SetsViewModel(mySetsList[i].setName, R.drawable.baseline_more_vert_24)
            )
        }

        val tabsAdapter = TabsRVAdapter(tabsData, this, this)
        val setsAdapter = SetsRVAdapter(setsData, this, this)

        tabsRecyclerView.adapter = tabsAdapter
        setsRecyclerView.adapter = setsAdapter

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

        createRecyclersView()
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

    override fun onDeleteSetClicked(position: Int) {
        tabsDBHelper.deleteOneSet(mySetsList[position])
        mySetsList.removeAt(position)
        createRecyclersView()
    }

    override fun onEditSetClicked(position: Int) {

    }

    override fun onAddSongToSetClicked(position: Int) {


        Intent(this, AddSongsToSetActivity::class.java).also {

            it.putExtra("EXTRA_SET_ID", mySetsList[position].setID)
            it.putExtra("EXTRA_SET_NAME", mySetsList[position].setName)

            startActivity(it)
        }
    }

        override fun onItemSetClick(position: Int) {

            Intent(this, SetActivity::class.java).also {

                it.putExtra("EXTRA_SET_ID", mySetsList[position].setID)
                it.putExtra("EXTRA_SET_NAME", mySetsList[position].setName)

                startActivity(it)
            }

        }

    }


















