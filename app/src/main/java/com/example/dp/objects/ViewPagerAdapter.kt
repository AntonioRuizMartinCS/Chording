package com.example.dp.objects

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import android.os.Build
import android.os.CountDownTimer
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dp.R
import com.example.dp.activities.BottomSheet
import com.example.dp.activities.EditTabActivity
import com.example.dp.databinding.TabItemBinding


class ViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    private val tabs: ArrayList<Song>) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    private var scrollTimer:CountDownTimer? = null
    private var defaultFontSize = 12F
    private var newFontSize = defaultFontSize
    var defaultTextMeasure = (defaultFontSize * 1.8).toInt()


    inner class ViewPagerViewHolder(val binding: TabItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tabName= binding.tabName
        val tabArtist = binding.tabArtist
        val tabTuning = binding.tabTuning
        val tabKey = binding.tabKey
        val tabCapo = binding.tabCapo
        val tabDuration = binding.tabDuration
        val tabChords = binding.tabChords
        val zoomBtn = binding.zoomBtn
        val zoomInBtn = binding.zoomIn
        val zoomOutBtn = binding.zoomOut
        val automaticScrollButton = binding.automaticScrollBtn
        val tabViewLinearLayout = binding.tabViewLinearLayout
        val editIconTabView = binding.editIconTabView
        val autoScrollOnBackground = ContextCompat.getDrawable(binding.root.context, R.drawable.a_s_on)
        val scrollView = binding.tabScreenScrollView
        val tabBodyTextView = binding.tabBodyTextView
        val moreBtn = binding.moreTabMenu

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding = TabItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {

        defaultFontSize = 12f
        newFontSize = defaultFontSize
        defaultTextMeasure = (defaultFontSize * 1.8).toInt()


        val chordFinder = ChordsFinder()
        var toggle = true
        val currentTab = tabs[position]


        // Access views and set data
        holder.tabName.text = currentTab.songName
        holder.tabArtist.text = currentTab.artist
        holder.tabTuning.text = "Tuning: ${currentTab.tuning}"
        holder.tabKey.text = "Key: ${currentTab.key}"
        holder.tabCapo.text = "Capo: ${currentTab.capo}"
        holder.tabDuration.text = "Duration: ${currentTab.minutes}:${currentTab.seconds}"
        holder.tabChords.text = currentTab.songChords.joinToString("  ")


        holder.zoomBtn.setOnClickListener {
            holder.zoomBtn.visibility = View.INVISIBLE
            holder.automaticScrollButton.visibility = View.INVISIBLE
            holder.zoomInBtn.visibility = View.VISIBLE
            holder.zoomOutBtn.visibility = View.VISIBLE
        }

        holder.zoomInBtn.setOnClickListener {
            zoomIn(holder, currentTab, chordFinder)
        }

        holder.zoomOutBtn.setOnClickListener {
            zoomOut(holder, holder.tabViewLinearLayout, currentTab, chordFinder)
        }

        holder.moreBtn.setOnClickListener {

            BottomSheet(currentTab).show(fragmentManager, "newTaskTag")


        }

        holder.tabViewLinearLayout.setOnClickListener {
            if (holder.zoomBtn.visibility == View.INVISIBLE){
                holder.zoomBtn.visibility = View.VISIBLE
                holder.zoomInBtn.visibility = View.INVISIBLE
                holder.zoomOutBtn.visibility = View.INVISIBLE
                holder.automaticScrollButton.visibility = View.VISIBLE
            }
        }

        holder.editIconTabView.setOnClickListener {

            Intent(holder.itemView.context, EditTabActivity::class.java).also {

                it.putExtra("EXTRA_SONG_ID", currentTab.id)
                it.putExtra("EXTRA_SONG_NAME", currentTab.songName)
                it.putExtra("EXTRA_SONG_ARTIST", currentTab.artist)
                it.putExtra("EXTRA_SONG_BODY", currentTab.songBody)
                it.putExtra("EXTRA_SONG_CAPO", currentTab.capo)
                it.putExtra("EXTRA_SONG_TUNING", currentTab.tuning)
                it.putExtra("EXTRA_SONG_KEY", currentTab.key)
                it.putExtra("EXTRA_SONG_CHORDS", currentTab.songChords)
                it.putExtra("EXTRA_MINUTES", currentTab.minutes)
                it.putExtra("EXTRA_SECONDS", currentTab.seconds)

                holder.itemView.context.startActivity(it)
            }

        }

        holder.automaticScrollButton.setOnClickListener {

            if (toggle){

                holder.automaticScrollButton.background = holder.autoScrollOnBackground
                triggerAS(holder, currentTab)
            }else{

                holder.automaticScrollButton.setBackgroundResource(R.drawable.zoom_in_out)
                stopAS()
            }
            toggle = !toggle

        }

        holder.automaticScrollButton.setOnLongClickListener {

            val dialogView = LayoutInflater.from(holder.itemView.context).inflate(R.layout.change_duration_dialog, null)
            val minutesPicker = dialogView.findViewById<NumberPicker>(R.id.minutesPickerDialog)
            val secondsPicker = dialogView.findViewById<NumberPicker>(R.id.secondsPickerDialog)
            var updatedMinutes = currentTab.minutes
            var updatedSeconds = currentTab.seconds

            val alert: AlertDialog.Builder = AlertDialog.Builder(holder.itemView.context)
            alert.setView(dialogView)

            alert.setTitle("Adjust duration of the song")

            minutesPicker.minValue = 0
            minutesPicker.maxValue = 60

            minutesPicker.value = currentTab.minutes

            secondsPicker.minValue = 0
            secondsPicker.maxValue = 60

            secondsPicker.value = currentTab.seconds

            minutesPicker.setOnValueChangedListener { _, _, newVal ->
                updatedMinutes = newVal
            }

            secondsPicker.setOnValueChangedListener { _, _, newVal ->
                updatedSeconds = newVal
            }

            alert.setPositiveButton("Ok") { _, _ ->
                updateTabDuration(updatedMinutes, updatedSeconds, holder.itemView.context, currentTab)

            }

            alert.setNegativeButton("Cancel",
                DialogInterface.OnClickListener { _, _ ->
                    // Canceled.
                })


            alert.show()

            true
        }


                    buildTabBody(defaultTextMeasure, defaultFontSize, holder, currentTab, chordFinder)

    }


    override fun getItemCount(): Int {

        return tabs.size
    }

    private fun updateTabDuration(minutes:Int, seconds:Int, context: Context, currentTab:Song){

        val dbHelper = TabsDBHelper(context)

        dbHelper.updateTable(currentTab.id, currentTab.songName, currentTab.artist, currentTab.songBody, currentTab.capo, currentTab.tuning, currentTab.key, currentTab.songChords.joinToString(" "), minutes, seconds)


    }

    private fun triggerAS(holder: ViewPagerViewHolder, currentTab: Song) {

        stopAS()

        //get the duration of the song in milliseconds
        val songTime = (currentTab.minutes * 60000) + (currentTab.seconds * 1000)


        //once the scroll view has been set
        holder.scrollView.post {
            //pixels that each scroll moves
            val heightToScroll = 2
            //height of the tab
            val viewHeight = holder.tabBodyTextView.measuredHeight
            //formula for how often should the intervals be
            val scrollPeriod = songTime/((viewHeight)/heightToScroll)

            scrollTimer = object : CountDownTimer(Long.MAX_VALUE, scrollPeriod.toLong()) {

                @SuppressLint("SetTextI18n")
                override fun onTick(millisUntilFinished: Long) {
                    holder.scrollView.scrollBy(0, heightToScroll)
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
    private fun zoomIn(holder: ViewPagerViewHolder, currentTab: Song, chordFinder: ChordsFinder){

        if (newFontSize<=24f){
            newFontSize += 1f
        }

        val newTextMeasure = (newFontSize*1.8).toInt()

        buildTabBody(newTextMeasure, newFontSize, holder, currentTab, chordFinder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun zoomOut(holder: ViewPagerViewHolder, tabViewLinearLayout: LinearLayout, currentTab: Song, chordFinder: ChordsFinder){


        if (newFontSize>=8f){
            newFontSize -= 1f
        }

        val textMeasure = (newFontSize*1.8).toInt()

        buildTabBody(textMeasure, newFontSize, holder,currentTab,chordFinder)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun buildTabBody(textSize: Int, fontSize:Float, holder: ViewPagerViewHolder, currentTab: Song, chordFinder: ChordsFinder
    ){



        val finalTabBody = SpannableStringBuilder()

        //flag for skipping iteration
        var skipNextIteration = false

        //initial array of unsorted lines
        val songLines:List<String> = currentTab.songBody.split("\n")

        //gets the width of layout after its been set
        holder.tabViewLinearLayout.post {


            //width of layout
            val layoutWidth = holder.tabViewLinearLayout.measuredWidth

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

                            val boldSpan1 = StyleSpan(Typeface.BOLD)

                            finalTabBody.setSpan(boldSpan1, finalTabBody.length - ((currentLine.substring(0, breakInLine).length)), finalTabBody.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

                            finalTabBody.append(songLines[i+1] + "\n")

                            finalTabBody.append((currentLine.substring(breakInLine,currentLine.length))+ "\n")

                            val boldSpan2 = StyleSpan(Typeface.BOLD)

                            finalTabBody.setSpan(boldSpan2, finalTabBody.length - (currentLine.substring(breakInLine,currentLine.length)).length - 1, finalTabBody.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)




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

                holder.tabBodyTextView.text = finalTabBody
                holder.tabBodyTextView.textSize = fontSize // Set text size

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
