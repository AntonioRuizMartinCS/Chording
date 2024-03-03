package com.example.dp.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.example.dp.databinding.ActivityTestBinding

class ActivityTest : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val spannableStringBuilder = SpannableStringBuilder()

        val stringArrayList = listOf(
            "This line contains the letter 'a'.",
            "Here is another line without the letter 'a'.",
            "Yet another line with the letter 'a' in it.",
            "This line does not have any 'a's in it.",
            "A line with a few 'a's scattered within.",
            "No 'a's here.",
            "One more line, not containing 'a'."
        )



        for (i in stringArrayList.indices){
            val currentLine = stringArrayList[i]
            val start = spannableStringBuilder.length
            spannableStringBuilder.append(currentLine)
            val end = spannableStringBuilder.length
            if (currentLine.contains('a')){
                val styleSpan = ForegroundColorSpan(Color.GREEN)
                spannableStringBuilder.setSpan(
                    styleSpan,
                    start,
                    end,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            if (i != stringArrayList.lastIndex) {
                spannableStringBuilder.append("\n") // Append newline to separate lines
            }
        }

        binding.testReadFile.text = spannableStringBuilder
    }
}