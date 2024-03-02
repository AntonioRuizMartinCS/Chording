package com.example.dp.activities

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dp.R
import com.example.dp.objects.Song
import com.example.dp.databinding.ActivityTabBinding
import com.example.dp.objects.ChordsFinder


class TabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding
    private lateinit var tab: Song
    private var defaultFontSize = 12f
    private var newFontSize = defaultFontSize
    private var defaultTextMeasure = (defaultFontSize * 1.8).toInt()
    private lateinit var tabBodyTextView:TextView
    private var tabViewLinearLayout:LinearLayout? = null

    @RequiresApi(Build.VERSION_CODES.R)
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
        buildTabBody(defaultTextMeasure, defaultFontSize)



    }


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun createTabViews(){

        val zoomBtn = binding.zoomBtn
        val zoomInBtn = binding.zoomIn
        val zoomOutBtn = binding.zoomOut

        binding.tabName.text = tab.songName
        binding.tabArtist.text = tab.artist
        binding.tabTuning.text = "Tuning: ${tab.tuning}"
        binding.tabKey.text = "Key: ${tab.key}"
        binding.tabCapo.text = "Capo: ${tab.capo}"
        binding.tabDuration.text = "Duration: ${tab.minutes}:${tab.seconds}"
        binding.tabChords.text = tab.songChords.joinToString(" ")

        zoomBtn.setOnClickListener {
            zoomBtn.visibility = View.INVISIBLE
            zoomInBtn.visibility = View.VISIBLE
            zoomOutBtn.visibility = View.VISIBLE
        }

        zoomInBtn.setOnClickListener {
            zoomIn()

        }

        zoomOutBtn.setOnClickListener {
            zoomOut()

        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun zoomIn(){

        if (newFontSize<=24f){
            newFontSize += 1f
        }

        val newTextMeasure = (newFontSize*1.8).toInt()

        tabViewLinearLayout?.removeView(tabBodyTextView)
        buildTabBody(newTextMeasure, newFontSize)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun zoomOut(){

        if (newFontSize>=8f){
            newFontSize -= 1f
        }

        val newTextMeasure = (newFontSize*1.8).toInt()

        tabViewLinearLayout?.removeView(tabBodyTextView)
        buildTabBody(newTextMeasure, newFontSize)

    }




    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildTabBody(textSize: Int, fontSize:Float){


        //flag for skipping iteration
        var skipNextIteration = false

        //final array of sorted lines
        val finalArray = arrayListOf<String>()

        //initial array of unsorted lines
        val songLines:List<String> = tab.songBody.split("\n")

        //gets the width of layout after its been set
        binding.tabViewLinearLayout.post {

            //width of layout
            val layoutWidth = binding.tabViewLinearLayout.measuredWidth



            //count of characters that fit in line
            val characterCapacity = layoutWidth/textSize

            //looping through each line
            for(i in songLines.indices){

                if (skipNextIteration){
                    skipNextIteration = false
                    continue
                }

                //current line
                val currentLine = songLines[i]

                //number characters in current line
                val songLineWidth = currentLine.length

                //check if line is wider than screen
                if (doesLineFit(characterCapacity, songLineWidth)){

                    //if fits add to final array
                        finalArray.add(songLines[i])

                    //if doesn't fit:
                }else{
                    //check if current and next lines are chords
                    val chordFinder = ChordsFinder()
                    val currentLineIsChords = chordFinder.isItAChordsLine(currentLine)
                    val nextLineIsChords = chordFinder.isItAChordsLine(songLines[i+1])

                    //check for different scenarios

                    //if current line is chords and next line is empty
                    if (currentLineIsChords && songLines[i+1].isBlank()){
                        val breakInLine =  findSpaceBeforeEnd(characterCapacity,currentLine)

                        finalArray.add(currentLine.substring(0, breakInLine))
                        finalArray.add(currentLine.substring(breakInLine,currentLine.length))

                    }

                    //if current line is lyrics
                    if (!currentLineIsChords){
                        val breakInLine =  findSpaceBeforeEnd(characterCapacity,currentLine)

                        finalArray.add(currentLine.substring(0, breakInLine))
                        finalArray.add(currentLine.substring(breakInLine,currentLine.length))

                    }

                    //if current line is chords and next line is lyrics
                    if (currentLineIsChords && !nextLineIsChords){

                        //if lyrics line fits
                        if (doesLineFit(characterCapacity, songLines[i+1].length)){

                            val breakInLine =  findSpaceBeforeEnd(characterCapacity,currentLine)

                            finalArray.add(currentLine.substring(0, breakInLine))
                            finalArray.add(songLines[i+1])
                            finalArray.add(currentLine.substring(breakInLine,currentLine.length))


                            //if lyrics line doesn't fit
                        }else{

                            finalArray.add(currentLine.substring(0, findSpaceBeforeEnd(characterCapacity, currentLine)))
                            finalArray.add(songLines[i+1].substring(0, findSpaceBeforeEnd(characterCapacity, songLines[i+1])))
                            finalArray.add(currentLine.substring(findSpaceBeforeEnd(characterCapacity, currentLine), currentLine.length))
                            finalArray.add(songLines[i+1].substring(findSpaceBeforeEnd(characterCapacity, songLines[i+1]), songLines[i+1].length))

                        }

                        skipNextIteration = true
                        continue


                    }
                }
            }


            //create text view programmatically
            tabViewLinearLayout = binding.tabViewLinearLayout
            tabBodyTextView = TextView(this)
            tabBodyTextView.id = View.generateViewId()
            tabBodyTextView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            tabBodyTextView.setPadding(0, 0, 0, 40) // Add padding if needed
            tabBodyTextView.text = finalArray.joinToString("\n")
            tabBodyTextView.textSize = fontSize // Set text size
            tabBodyTextView.typeface = resources.getFont(R.font.cousine) // Set font
            tabBodyTextView.setTextColor(resources.getColor(android.R.color.black)) // Set text color

            tabViewLinearLayout?.addView(tabBodyTextView)

        }

    }

            private fun findSpaceBeforeEnd(characterCapacity: Int, lyricLine: String): Int {

                var breakInLyrics = 0
                //find a space to break at
                for (j in characterCapacity downTo 1) {
                    if (lyricLine[j - 1] == ' ') {
                        breakInLyrics = j
                        break
                    }
                }
                return breakInLyrics
            }

            private fun doesLineFit(characterCapacity: Int, songLineWith: Int):Boolean{

                if (songLineWith<= characterCapacity){
                    return true
                }
                return false

            }


}

