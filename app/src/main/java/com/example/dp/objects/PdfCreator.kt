package com.example.dp.objects


import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.print.PrintAttributes
import android.print.PrintManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.dp.R
import java.io.File
import java.io.FileOutputStream
import kotlin.math.ceil

class PdfCreator {
    private var pageHeight = 1120
    private var pageWidth = 792


    fun generatePDF(context: Context, tab:Song) {

        //creating instance of PdfDocument
        val pdfDocument = android.graphics.pdf.PdfDocument()

        //setting our print attributes
        val printAttributes = PrintAttributes.Builder()
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setColorMode(PrintAttributes.COLOR_MODE_COLOR)
            .build()

        val songName = Paint()
        songName.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        songName.textSize = 30F
        songName.color = ContextCompat.getColor(context, R.color.accent)
        songName.textAlign = Paint.Align.LEFT

        val songArtist = Paint()
        songArtist.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        songArtist.textSize = 25F
        songArtist.color = ContextCompat.getColor(context, R.color.black)
        songArtist.textAlign = Paint.Align.LEFT

        val songAttributes = Paint()
        songAttributes.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        songAttributes.textSize = 20F
        songAttributes.color = ContextCompat.getColor(context, R.color.black)
        songAttributes.textAlign = Paint.Align.LEFT

        val body = Paint()
        body.typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
        body.textSize = 18F
        body.color = ContextCompat.getColor(context, R.color.black)
        body.textAlign = Paint.Align.LEFT

        val myPageInfo:PdfDocument.PageInfo? = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        val myPage = pdfDocument.startPage(myPageInfo)

        val canvas = myPage.canvas


        canvas.drawText(tab.songName, 50F, 50F, songName)

        canvas.drawText(tab.artist, 50F, 80F, songArtist)

        canvas.drawText("Tuning: ${tab.tuning}", 50F, 110F, songAttributes)
        canvas.drawText("Key: ${tab.key}", 50F, 140F, songAttributes)
        canvas.drawText("Capo: ${tab.capo}", 50F, 170F, songAttributes)
        canvas.drawText("Duration: " + tab.minutes.toString() + ":" + tab.seconds.toString(), 50F, 200F, songAttributes)


        val songLines = tab.songBody.split("\n")

        val numberOfLines = songLines.size

        val numberOfLinesOn1stPage = 29


        val numberOfLinesOnNewPage = 36
        val pagesNeeded = ceil((((numberOfLines * 30) - 880) / 1090).toDouble()).toInt() + 1

        var z = 240F

        for (i in 0..numberOfLinesOn1stPage){
            canvas.drawText(songLines[i], 50F, z, body)
            z += 30F
        }

        pdfDocument.finishPage(myPage)


       if (pagesNeeded == 1){

           val newPage = pdfDocument.startPage(myPageInfo)
           val newCanvas = newPage.canvas
           z = 30F

           for (i in numberOfLinesOn1stPage +1..< numberOfLines){
               newCanvas.drawText(songLines[i], 50F, z, body)
               z += 30F
           }
           pdfDocument.finishPage(newPage)

       }


        if (pagesNeeded == 2){

            val newPage = pdfDocument.startPage(myPageInfo)
            val newCanvas = newPage.canvas
            z = 30F

            for (i in numberOfLinesOn1stPage +1 until numberOfLinesOn1stPage + numberOfLinesOnNewPage){
                newCanvas.drawText(songLines[i], 50F, z, body)
                z += 30F
            }
            pdfDocument.finishPage(newPage)

            val newPage1 = pdfDocument.startPage(myPageInfo)
            val newCanvas1 = newPage1.canvas

            z = 30F

            for (i in numberOfLinesOn1stPage + numberOfLinesOnNewPage +1 until numberOfLines){
                newCanvas1.drawText(songLines[i], 50F, z, body)
                z += 30F
            }
            pdfDocument.finishPage(newPage1)

        }

        val file = File(context.filesDir, "${tab.songName}.pdf")

        try {


            pdfDocument.writeTo(FileOutputStream(file))

        } catch (e: Exception) {


            e.printStackTrace()


            Toast.makeText(context, "Fail to generate PDF file..", Toast.LENGTH_SHORT)
                .show()
        }


        pdfDocument.close()

            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager

            try {
                printManager.print("Documents", PdfDocumentAdapter(context, context.filesDir.path + "/${tab.songName}.pdf"), printAttributes)
            }catch (e:Exception){
            Log.e("DP",""+e.message)
        }

    }


}