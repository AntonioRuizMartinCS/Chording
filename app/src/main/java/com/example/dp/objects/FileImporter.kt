package com.example.dp.objects

class FileImporter(
) {

//    https://kotlinandroid.org/kotlin/kotlin-find-index-of-substring-in-a-string/

    fun findSongName(importedSongLines: ArrayList<String>): String {

        return importedSongLines[0]

    }

    fun findSongArtist(importedSongLines: ArrayList<String>): String {

        return importedSongLines[1]

    }

    fun findSongKey(importedSongLines: ArrayList<String>): String {

        val songKeyLine = importedSongLines[2]

        return songKeyLine.substring(songKeyLine.lastIndexOf(":") + 1, songKeyLine.length)

    }

    fun findSongMinutes(importedSongLines: ArrayList<String>): Int{

        var minutes = 0


        for (importedSongLine in importedSongLines){
            if (importedSongLine.contains("duration", ignoreCase = true)){

            minutes = importedSongLine.substring(importedSongLine.indexOf(":")+1, importedSongLine.lastIndexOf(":")).trim().toInt()

            }
        }

        return minutes

    }

    fun findSongSeconds(importedSongLines: ArrayList<String>): Int{

        var seconds = 0

        for (importedSongLine in importedSongLines){

            //find the line that indicated duration
            if (importedSongLine.contains("duration", ignoreCase = true)) {

                //finding the value inside the line
                seconds = importedSongLine.substring(importedSongLine.lastIndexOf(":") + 1).trim().toInt()
            }
        }
        return seconds

    }

    fun findSongBody(importedSongLines: ArrayList<String>): String {

        return importedSongLines.slice(5 until importedSongLines.size).joinToString("\n")

    }


}