package com.example.dp.activities

import android.annotation.SuppressLint
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dp.objects.Song
import com.example.dp.databinding.ActivityTabBinding


class TabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding
    private lateinit var tab: Song
    private var paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTabBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        tab = Song(intent.getIntExtra("EXTRA_SONG_ID", 0),

            intent.getStringExtra("EXTRA_SONG_NAME").toString(),
            intent.getStringExtra("EXTRA_SONG_ARTIST").toString(),
            intent.getStringExtra("EXTRA_SONG_BODY").toString(),
            intent.getStringExtra("EXTRA_SONG_CAPO").toString(),
            intent.getStringExtra("EXTRA_SONG_TUNING").toString(),
            intent.getStringExtra("EXTRA_SONG_KEY").toString(),
            intent.getSerializableExtra("EXTRA_SONG_CHORDS") as ArrayList<String>,
            intent.getIntExtra("EXTRA_MINUTES", 0),
            intent.getIntExtra("EXTRA_SECONDS", 0)


        )


        createTabViews()
        buildTabBody(36)


    }


    @SuppressLint("SetTextI18n")
    private fun createTabViews(){

        binding.tabName.text = tab.songName
        binding.tabArtist.text = tab.artist
        binding.tabTuning.text = "Tuning: ${tab.tuning}"
        binding.tabKey.text = "Key: ${tab.key}"
        binding.tabCapo.text = "Capo: ${tab.capo}"
        binding.tabDuration.text = "Duration: ${tab.minutes}:${tab.seconds}"
        binding.tabChords.text = tab.songChords.joinToString(" ")


    }

    private fun buildTabBody(textSize: Int){


        val finalArray = arrayListOf<String>()
        val songLines:List<String> = tab.songBody.split("\n")

        //gets the width of layout after its been set
        binding.tabViewLinearLayout.post {

            val layoutWidth = binding.tabBody.measuredWidth

            //count of characters
            val characterCapacity = layoutWidth/textSize.toInt()

            for(i in songLines.indices){

                //number characters in line
                val songLineWidth = songLines[i].length

                //check if line is wider than screen
                if (songLineWidth<=characterCapacity){

                    if (songLines[i] == ""){
                        finalArray.add(songLines[i])

                    }

                    if(!finalArray.contains(songLines[i])){

                        finalArray.add(songLines[i])
                    }


                    //if it is:
                }else{

                    var endIndex = 0

                    for(j in characterCapacity downTo 1){
                        if (songLines[i][j-1] == ' '){

                            endIndex = j
                            break
                        }
                    }

                    //adding the substring that fits in screen without cutting words
                    finalArray.add(songLines[i].substring(0, endIndex))
                    finalArray.add(songLines[i+1])
                    finalArray.add(songLines[i].substring(endIndex, songLines[i].length))
                }

            }

            binding.tabBody.text = finalArray.joinToString("\n")
        }
    }
}

