package com.example.dp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dp.objects.Song
import com.example.dp.databinding.ActivityTabBinding

class TabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabBinding
    private lateinit var tab: Song

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
            intent.getStringExtra("EXTRA_SONG_STYLE").toString(),
            intent.getStringExtra("EXTRA_SONG_TUNING").toString(),
            intent.getStringExtra("EXTRA_SONG_KEY").toString(),
            intent.getSerializableExtra("EXTRA_SONG_CHORDS") as ArrayList<String>,
            intent.getIntExtra("EXTRA_MINUTES", 0),
            intent.getIntExtra("EXTRA_SECONDS", 0)


        )

        createTabViews()

    }

    private fun createTabViews(){

        binding.tabName.text = tab.songName
        binding.tabArtist.text = tab.artist
        binding.tabTuning.text = tab.tuning
        binding.tabKey.text = tab.key
        binding.tabCapo.text = tab.capo
        binding.tabChords.text = tab.songChords.joinToString(" ")
        binding.tabBody.text = tab.songBody
        binding.minutes.text = tab.minutes.toString()
        binding.seconds.text = tab.seconds.toString()

    }
}