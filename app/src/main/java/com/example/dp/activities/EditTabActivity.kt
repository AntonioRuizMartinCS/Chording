package com.example.dp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.dp.databinding.ActivityEditTabBinding
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.dp.R
import com.example.dp.objects.ChordsFinder
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper
import com.google.android.material.switchmaterial.SwitchMaterial


class EditTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTabBinding
    private lateinit var tab: Song
    var capo = ""
    var style = ""
    var tuning = ""
    var key = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTabBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tab = Song(intent.getIntExtra("EXTRA_SONG_ID", 0),

            intent.getStringExtra("EXTRA_SONG_NAME").toString(),
            intent.getStringExtra("EXTRA_SONG_ARTIST").toString(),
            intent.getStringExtra("EXTRA_SONG_BODY").toString(),
            intent.getStringExtra("EXTRA_SONG_CAPO").toString(),
            intent.getStringExtra("EXTRA_SONG_STYLE").toString(),
            intent.getStringExtra("EXTRA_SONG_TUNING").toString(),
            intent.getStringExtra("EXTRA_SONG_KEY").toString(),
            intent.getSerializableExtra("EXTRA_SONG_CHORDS") as ArrayList<String>,
            intent.getIntExtra("EXTRA_MINUTES", 0),
            intent.getIntExtra("EXTRA_SECONDS", 0)
        )

        createEditingForm()
        buildActivityViews()
    }

    private fun createEditingForm(){

        binding.editArtistName.setText(tab.artist)
        binding.editSongTitle.setText(tab.songName)
        binding.songBody.setText(tab.songBody)

    }


    private fun buildActivityViews(){

        val capoSpinner: Spinner = binding.capoSpinner
        val tuningSpinner: Spinner = binding.tuningSpinner
        val keySpinner: Spinner = binding.keySpinner

        ArrayAdapter.createFromResource(
            this,
            R.array.capo,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            capoSpinner.adapter = adapter

            //    https://stackoverflow.com/questions/10634180/how-to-set-spinner-default-by-its-value-instead-of-position
            capoSpinner.setSelection(adapter.getPosition(tab.capo))

        }


        ArrayAdapter.createFromResource(
            this,
            R.array.tuning,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            tuningSpinner.adapter = adapter

            tuningSpinner.setSelection(adapter.getPosition(tab.tuning))
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.key,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            keySpinner.adapter = adapter

            keySpinner.setSelection(adapter.getPosition(tab.key))
        }



        capoSpinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                capo = capoSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")

            }
        }


        tuningSpinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                tuning = tuningSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        keySpinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                key = keySpinner.selectedItem.toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        val toggle: SwitchMaterial = binding.advancedFieldsSwitch
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.advancedFields.visibility = View.VISIBLE
                binding.generalFields.visibility = View.INVISIBLE
            } else {
                binding.advancedFields.visibility = View.INVISIBLE
                binding.generalFields.visibility = View.VISIBLE

            }
        }

        val saveBtn = binding.saveBtn

        saveBtn.setOnClickListener{
            updateTab()
        }
    }

    private fun updateTab(){

        val updatedSongName = binding.editSongTitle.text.toString()
        val updatedArtist = binding.editArtistName.text.toString()
        val updatedSongBody = binding.songBody.text.toString()
        val chordsFinder = ChordsFinder(updatedSongBody.split("\r\n"))
        val updatedSongChords = chordsFinder.findChords(updatedSongBody.split("\r\n"))

        val dbHelper = TabsDBHelper(this)
        val updatedSong = Song(tab.id, updatedSongName, updatedArtist, updatedSongBody, capo, style, tuning, key, updatedSongChords, tab.minutes, tab.seconds)

        dbHelper.updateTable(tab.id, updatedSong.songName, updatedSong.artist, updatedSong.songBody, capo, style, tuning, key, updatedSongChords.joinToString(" "), tab.minutes, tab.seconds)

        Intent(this, MainActivity::class.java).also {

            startActivity(it)

        }

    }









}