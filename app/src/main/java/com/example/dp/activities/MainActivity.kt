package com.example.dp.activities



import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
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
    private var tabsDBHelper =  TabsDBHelper(this)
    private lateinit var myTabsList:ArrayList<Song>
    private lateinit var mySetsList:ArrayList<com.example.dp.objects.Set>
    private lateinit var searchView:SearchView
    private lateinit var tabsAdapter:TabsRVAdapter





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

        updateTabsAndSets()
        createRecyclersView()
        createButtons()
        createTransition(binding.myTabsLabel)
    }

    override fun onRestart() {

        updateTabsAndSets()

        super.onRestart()
    }

    private fun updateTabsAndSets() {
        myTabsList = tabsDBHelper.allTabs
        mySetsList = tabsDBHelper.allSets
    }


    //    https://stackoverflow.com/questions/2614545/animate-change-of-view-background-color-on-android
    private fun createTransition(view: TextView){

        val transition = view.background as? TransitionDrawable
        transition?.startTransition(500)
    }

    private fun resetTransition(view: TextView){
        val transitionDrawable = ContextCompat.getDrawable(this, R.drawable.bottom_line_transition)
        view.background = transitionDrawable
    }



    private fun createButtons(){

        val setsBtn = binding.mySetsLabel
        val tabsBtn = binding.myTabsLabel

        updateAddButtonClickListener()


        setsBtn.setOnClickListener{
            binding.setsRecyclerView.visibility = View.VISIBLE
            binding.tabsRecyclerView.visibility = View.INVISIBLE
            createTransition(setsBtn)
            resetTransition(tabsBtn)
            updateAddButtonClickListener()

        }
        tabsBtn.setOnClickListener{

            binding.setsRecyclerView.visibility = View.INVISIBLE
            binding.tabsRecyclerView.visibility = View.VISIBLE
            createTransition(tabsBtn)
            resetTransition(setsBtn)
            updateAddButtonClickListener()

        }

        binding.sortByButton.setOnClickListener {

                createSortByMenu()

        }
    }



    private fun updateAddButtonClickListener() {
        val addBtn = binding.addBtn

//        https://stackoverflow.com/questions/4134117/edittext-on-a-popup-window
        if (binding.setsRecyclerView.visibility == View.VISIBLE) {
            addBtn.setOnClickListener {
                val alert: AlertDialog.Builder = AlertDialog.Builder(this)
                alert.setTitle("add set name")

                val input = EditText(this)
                alert.setView(input)

                alert.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
                    val setID = mySetsList.size + 1
                    val setName = input.text.toString()

                    val newSet = com.example.dp.objects.Set(setID, setName)
                    tabsDBHelper.addOneSet(newSet)

                    mySetsList = tabsDBHelper.allSets
                    createRecyclersView()
                })

                alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
                    // Canceled.
                })

                alert.show()
            }
        } else {
            addBtn.setOnClickListener {
                Intent(this, UploadActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun createSortByMenu(){

        val popupMenu = PopupMenu(this, binding.sortByButton)

        popupMenu.menuInflater.inflate(R.menu.sort_by_context_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->

            when(item.itemId){

                R.id.sortAZ ->{

                    myTabsList.sortWith(compareByDescending<Song> { it.songName}.reversed())
                    createRecyclersView()
                    binding.sortByButton.text = "A - Z"
                }

                R.id.sortByArtist -> {

                    myTabsList.sortWith(compareByDescending<Song> { it.artist}.reversed())
                    createRecyclersView()
                    binding.sortByButton.text = "By artist"
                }

                R.id.sortByDate -> {

                    myTabsList.sortWith(compareByDescending<Song> { it.id}.reversed())
                    createRecyclersView()
                    binding.sortByButton.text = "By date"
                }

            }
            true
        }
        popupMenu.show()

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

        tabsAdapter = TabsRVAdapter(tabsData, this, this)
        val setsAdapter = SetsRVAdapter(setsData, this, this)

        tabsRecyclerView.adapter = tabsAdapter
        setsRecyclerView.adapter = setsAdapter


        searchView = binding.searchView

        searchView.isActivated = true
        searchView.queryHint = "Search tab by song name"
        searchView.onActionViewExpanded()
        searchView.isIconified = false
        searchView.clearFocus()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })




    }

//    https://www.geeksforgeeks.org/android-searchview-with-recyclerview-using-kotlin/
    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList: ArrayList<TabsViewModel> = ArrayList()

        // running a for loop to compare elements.
        for (item in myTabsList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.songName.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(TabsViewModel(item.songName, item.artist, R.drawable.baseline_more_vert_24))
            }
        }

            tabsAdapter.filterList(filteredList)

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

        val alert: AlertDialog.Builder = AlertDialog.Builder(this)

        alert.setTitle("Edit set name")

        // Set an EditText view to get user input

        // Set an EditText view to get user input
        val input = EditText(this)
        input.hint = mySetsList[position].setName
        alert.setView(input)

        alert.setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->
            // Do something with value!

            val updatedSetName = input.text.toString()

            tabsDBHelper.updateSetName(mySetsList[position].setID, updatedSetName)
            updateTabsAndSets()
            createRecyclersView()

        })

        alert.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { _, _ ->
                // Canceled.
            })

        alert.show()

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


















