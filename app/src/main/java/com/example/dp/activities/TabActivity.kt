package com.example.dp.activities


import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.dp.R
import com.example.dp.databinding.ActivityTabBinding
import com.example.dp.objects.ChordsFinder
import com.example.dp.objects.Song


class TabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding
    private lateinit var tab: Song
    private var defaultFontSize = 12f
    private var newFontSize = defaultFontSize
    private var defaultTextMeasure = (defaultFontSize * 1.8).toInt()
    private lateinit var tabBodyTextView:TextView
    private var tabViewLinearLayout:LinearLayout? = null
    private val chordFinder = ChordsFinder()
    private var scrollTimer: CountDownTimer? = null

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
        val automaticScrollButton = binding.automaticScrollBtn

        binding.tabName.text = tab.songName
        binding.tabArtist.text = tab.artist
        binding.tabTuning.text = "Tuning: ${tab.tuning}"
        binding.tabKey.text = "Key: ${tab.key}"
        binding.tabCapo.text = "Capo: ${tab.capo}"
        binding.tabDuration.text = "Duration: ${tab.minutes}:${tab.seconds}"
        binding.tabChords.text = tab.songChords.joinToString(" ")

        zoomBtn.setOnClickListener {
            zoomBtn.visibility = View.INVISIBLE
            automaticScrollButton.visibility = View.INVISIBLE
            zoomInBtn.visibility = View.VISIBLE
            zoomOutBtn.visibility = View.VISIBLE
        }

        zoomInBtn.setOnClickListener {
            zoomIn()

        }

        zoomOutBtn.setOnClickListener {
            zoomOut()

        }
        binding.tabViewLinearLayout.setOnClickListener {
            if (zoomBtn.visibility == View.INVISIBLE){
                zoomBtn.visibility = View.VISIBLE
                zoomInBtn.visibility = View.INVISIBLE
                zoomOutBtn.visibility = View.INVISIBLE
                automaticScrollButton.visibility = View.VISIBLE
            }


        }


        //toggle automatic scroll with button

        var toggle = true
        automaticScrollButton.setOnClickListener {

            if (toggle){

                automaticScrollButton.background = ColorDrawable(Color.MAGENTA)
                triggerAS()

            }else{

                automaticScrollButton.setBackgroundResource(R.drawable.zoom_in_out)
                stopAS()

            }
            toggle = !toggle

        }





    }


//    https://stackoverflow.com/questions/13952357/infinite-auto-scroll-listview-with-scroll-speed-controlled
    private fun triggerAS() {


    stopAS()

    //get the duration of the song in milliseconds
    val songTime = (tab.minutes * 60000) + (tab.seconds * 1000)

    val scrollView = findViewById<ScrollView>(R.id.tabScreenScrollView)


        //once the scroll view has been set
        scrollView.post {
            //pixels that each scroll moves
            val heightToScroll = 2
            //height of the tab
            val viewHeight = tabBodyTextView.measuredHeight
            //accounting for the space above the tab view to have some margin
            val marginSpace = tabViewLinearLayout?.measuredHeight?.minus(tabBodyTextView.measuredHeight)
            //formula for how often should the intervals be
            val scrollPeriod = songTime/((viewHeight- marginSpace!!)/heightToScroll)

            scrollTimer = object : CountDownTimer(songTime.toLong(), scrollPeriod.toLong()) {

                    @SuppressLint("SetTextI18n")
                    override fun onTick(millisUntilFinished: Long) {
                        scrollView.scrollBy(0, heightToScroll)
                        binding.countdown.text = "seconds remaining: " + millisUntilFinished / 1000
                    }

                    override fun onFinish() {
                        // Add code for restarting timer here if needed

                    }
                }.start()

        }
    }

    private fun stopAS() {
        // Check if there's an ongoing scrolling and cancel it
        scrollTimer?.cancel()
        scrollTimer = null
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


        val finalTabBody = SpannableStringBuilder()



        //flag for skipping iteration
        var skipNextIteration = false

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
                        finalTabBody.append(songLines[i] + "\n")

                    //set style if its chords line
                    if (chordFinder.isItAChordsLine(songLines[i])){
                        val boldSpan = StyleSpan(Typeface.BOLD)

                        finalTabBody.setSpan(boldSpan, finalTabBody.length - songLines[i].length - 1 , finalTabBody.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                    }



                    //if doesn't fit:
                }else{
                    //check if current and next lines are chords

                    val currentLineIsChords = chordFinder.isItAChordsLine(currentLine)
                    val nextLineIsChords = chordFinder.isItAChordsLine(songLines[i+1])

                    //check for different scenarios

                    //if current line is chords and next line is empty
                    if (currentLineIsChords && songLines[i+1].isBlank()){
                        val breakInLine =  findSpaceBeforeEnd(characterCapacity,currentLine)

                        finalTabBody.append((currentLine.substring(0, breakInLine))+ "\n")
                        finalTabBody.append((currentLine.substring(breakInLine,currentLine.length))+ "\n")

                        val boldSpan = StyleSpan(Typeface.BOLD)

                        finalTabBody.setSpan(boldSpan, finalTabBody.length - currentLine.length - 1 , finalTabBody.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)



                    }

                    //if current line is lyrics
                    if (!currentLineIsChords){
                        val breakInLine =  findSpaceBeforeEnd(characterCapacity,currentLine)

                        finalTabBody.append((currentLine.substring(0, breakInLine))+ "\n")
                        finalTabBody.append((currentLine.substring(breakInLine,currentLine.length))+ "\n")

                    }

                    //if current line is chords and next line is lyrics
                    if (currentLineIsChords && !nextLineIsChords){

                        //if lyrics line fits
                        if (doesLineFit(characterCapacity, songLines[i+1].length)){

                            val breakInLine =  findSpaceBeforeEnd(characterCapacity,currentLine)

                            finalTabBody.append((currentLine.substring(0, breakInLine))+ "\n")
                            finalTabBody.append(songLines[i+1] + "\n")
                            finalTabBody.append((currentLine.substring(breakInLine,currentLine.length))+ "\n")


                            //if lyrics line doesn't fit
                        }else{

                            finalTabBody.append((currentLine.substring(0, findSpaceBeforeEnd(characterCapacity, currentLine)))+ "\n")
                            finalTabBody.append((songLines[i+1].substring(0, findSpaceBeforeEnd(characterCapacity, songLines[i+1])))+ "\n")
                            finalTabBody.append((currentLine.substring(findSpaceBeforeEnd(characterCapacity, currentLine), currentLine.length))+ "\n")
                            finalTabBody.append((songLines[i+1].substring(findSpaceBeforeEnd(characterCapacity, songLines[i+1]), songLines[i+1].length))+ "\n")

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
            tabBodyTextView.text = finalTabBody
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

