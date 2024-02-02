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

    fun findSongDuration(importedSongLines: ArrayList<String>): String {

        val songKeyLine = importedSongLines[4]

        return songKeyLine.substring(songKeyLine.lastIndexOf(":") + 1, songKeyLine.length)

    }

    fun findSongBody(importedSongLines: ArrayList<String>): String {

        return importedSongLines.slice(5 until importedSongLines.size).joinToString("\n")

    }


}