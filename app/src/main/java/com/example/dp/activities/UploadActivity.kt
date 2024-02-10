package com.example.dp.activities
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dp.R
import com.example.dp.databinding.ActivityUploadBinding
import com.example.dp.objects.ChordsFinder
import com.example.dp.objects.FileImporter
import com.example.dp.objects.Song
import com.example.dp.objects.TabsDBHelper
import com.google.android.material.switchmaterial.SwitchMaterial
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private var tabs: ArrayList<Song> = arrayListOf()
    var capo = ""
    var tuning = ""
    var key = ""
    private var seconds = 0
    private var minutes = 0
    private val dbHelper = TabsDBHelper(this)




    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root



        setContentView(view)
        buildActivityViews()

    }




        @RequiresApi(Build.VERSION_CODES.Q)
        private fun buildActivityViews(){

            val capoSpinner: Spinner = binding.capoSpinner
            val tuningSpinner: Spinner = binding.tuningSpinner
            val keySpinner: Spinner = binding.keySpinner
            val minutesPicker: NumberPicker = binding.durationPickerMinutes
            val secondsPicker: NumberPicker = binding.durationPickerSeconds




            binding.numberOfTabs.text = dbHelper.allTabs.size.toString()


//            https://medium.com/@sc71/android-numberpickers-3ef535c45487
            minutesPicker.minValue = 0
            minutesPicker.maxValue = 60

            secondsPicker.minValue = 0
            secondsPicker.maxValue = 60


            secondsPicker.setOnValueChangedListener { _, _, newVal ->
                seconds = newVal -1
            }
            minutesPicker.setOnValueChangedListener { _, _, newVal ->
                minutes = newVal -1
            }




            ArrayAdapter.createFromResource(
                this,
                R.array.capo,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                capoSpinner.adapter = adapter

            }

            ArrayAdapter.createFromResource(
                this,
                R.array.tuning,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tuningSpinner.adapter = adapter
            }

            ArrayAdapter.createFromResource(
                this,
                R.array.key,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                keySpinner.adapter = adapter
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
                uploadSong()
            }

            val importButton = binding.importBtn

            importButton.setOnClickListener{

                openFile()

            }
    }

//    https://developer.android.com/training/data-storage/shared/documents-files#open-file
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun openFile(){
        val pickFileRequestCode = 1

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "*/*"
        startActivityForResult(intent, pickFileRequestCode)
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == 1
            && resultCode == Activity.RESULT_OK) {
            // The result data contains a URI for the document or directory that
            // the user selected.
            resultData?.data?.also { uri ->

                val fileImporter = FileImporter()

                binding.editSongTitle.setText(fileImporter.findSongName(readTextFromUri(uri)))
                binding.editArtistName.setText(fileImporter.findSongArtist(readTextFromUri(uri)))
                binding.songBody.setText(fileImporter.findSongBody(readTextFromUri(uri)))
                binding.durationPickerMinutes.value = fileImporter.findSongMinutes(readTextFromUri(uri))
                binding.durationPickerSeconds.value = fileImporter.findSongSeconds(readTextFromUri(uri))


            }
        }
    }



    @Throws(IOException::class)
    private fun readTextFromUri(uri: Uri): ArrayList<String> {

        val importedSongLines = ArrayList<String>()
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BufferedReader(InputStreamReader(inputStream)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {

                    importedSongLines.add(line)
                    line = reader.readLine()

                }
            }
        }
        return importedSongLines
    }


    private fun uploadSong(){

        val songID = tabs.size + 1
        val songName = binding.editSongTitle.text.toString()
        val artist = binding.editArtistName.text.toString()
        val songBody = binding.songBody.text.toString()
        val chordsFinder = ChordsFinder()
        val songChords: ArrayList<String> = chordsFinder.findChords(songBody.split("\r\n"))


        val song = Song(songID, songName, artist, songBody, capo, tuning, key, songChords, minutes, seconds)

        tabs.add(song)

        binding.numberOfTabs.text = tabs.size.toString()

        val dbHelper = TabsDBHelper(this)

        val success:Boolean = dbHelper.addOne(song)

        if (success){
            Toast.makeText(this, "Tab was saved successfully", Toast.LENGTH_SHORT).show()
        }

        Intent(this, MainActivity::class.java).also {

            startActivity(it)

        }
    }
}