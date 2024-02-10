package com.example.dp.objects

class ChordsFinder {

    private val lineDescriptions: ArrayList<String> = arrayListOf(

        "verse", "intro", "instrumental", "verse:", "intro:", "instrumental:"

    )

     private val musicChords: ArrayList<String> = arrayListOf(
        // C Chords
        "C", "Cm", "C7", "C5", "Cdim", "Cdim7", "Caug", "Csus2", "Csus", "Cmaj7", "Cm7", "C7sus4", "Cmaj9", "Cmaj11", "Cmaj13", "Cmaj9(#11)", "Cmaj13(#11)", "Cadd9", "C6add9", "Cmaj7(b5)", "Cmaj7(#5)", "Cm6", "Cm9", "Cm11", "Cm13", "Cm(add9)", "Cm6add9", "Cmmaj7", "Cmmaj9", "Cm7b5", "Cm7#5", "C6", "C9", "C11", "C13", "C7b5", "C7#5", "C7b9", "C7#9", "C7(b5)", "C7(#5)", "C9b5", "C9#5", "C13#11", "C13b9", "C11b9", "Csus2sus4", "C-5",

        // C# Chords
        "C#m", "C#7", "C#m7", "C#7sus4", "C#maj9", "C#maj11", "C#maj13", "C#maj9(#11)", "C#maj13(#11)", "C#add9", "C#6add9", "C#maj7(b5)", "C#maj7(#5)", "C#m6", "C#m9", "C#m11", "C#m13", "C#m(add9)", "C#m6add9", "C#mmaj7", "C#mmaj9", "C#m7b5", "C#m7#5", "C#6", "C#9", "C#11", "C#13", "C#7b5", "C#7#5", "C#7b9", "C#7#9", "C#7(b5)", "C#7(#5)", "C#9b5", "C#9#5", "C#13#11", "C#13b9", "C#11b9", "C#sus2sus4", "C#-5",

        // C#/Db Chords
        "C#/Db", "C#/Dbm", "C#/Db7", "C#/Db5", "C#/Dbdim", "C#/Dbdim7", "C#/Dbaug", "C#/Dbsus2", "C#/Dbsus", "C#/Dbmaj7", "C#/Dbm7", "C#/Db7sus4", "C#/Dbmaj9", "C#/Dbmaj11", "C#/Dbmaj13", "C#/Dbmaj9(#11)", "C#/Dbmaj13(#11)", "C#/Dbadd9", "C#/Db6add9", "C#/Dbmaj7(b5)", "C#/Dbmaj7(#5)", "C#/Dbm6", "C#/Dbm9", "C#/Dbm11", "C#/Dbm13", "C#/Dbm(add9)", "C#/Dbm6add9", "C#/Dbmmaj7", "C#/Dbmmaj9", "C#/Dbm7b5", "C#/Dbm7#5", "C#/Db6", "C#/Db9", "C#/Db11", "C#/Db13", "C#/Db7b5", "C#/Db7#5", "C#/Db7b9", "C#/Db7#9", "C#/Db7(b5)", "C#/Db7(#5)", "C#/Db9b5", "C#/Db9#5", "C#/Db13#11", "C#/Db13b9", "C#/Db11b9", "C#/Dbsus2sus4", "C#/Db-5",

        // D Chords
        "D", "Dm", "D7", "D5", "Ddim", "Ddim7", "Daug", "Dsus2", "Dsus", "Dmaj7", "Dm7", "D7sus4", "Dmaj9", "Dmaj11", "Dmaj13", "Dmaj9(#11)", "Dmaj13(#11)", "Dadd9", "D6add9", "Dmaj7(b5)", "Dmaj7(#5)", "Dm6", "Dm9", "Dm11", "Dm13", "Dm(add9)", "Dm6add9", "Dmmaj7", "Dmmaj9", "Dm7b5", "Dm7#5", "D6", "D9", "D11", "D13", "D7b5", "D7#5", "D7b9", "D7#9", "D7(b5)", "D7(#5)", "D9b5", "D9#5", "D13#11", "D13b9", "D11b9", "Dsus2sus4", "D-5",

        // D#/Eb Chords
        "D#/Eb", "D#/Ebm", "D#/Eb7", "D#/Eb5", "D#/Ebdim", "D#/Ebdim7", "D#/Ebaug", "D#/Ebsus2", "D#/Ebsus", "D#/Ebmaj7", "D#/Ebm7", "D#/Eb7sus4", "D#/Ebmaj9", "D#/Ebmaj11", "D#/Ebmaj13", "D#/Ebmaj9(#11)", "D#/Ebmaj13(#11)", "D#/Ebadd9", "D#/Eb6add9", "D#/Ebmaj7(b5)", "D#/Ebmaj7(#5)", "D#/Ebm6", "D#/Ebm9", "D#/Ebm11", "D#/Ebm13", "D#/Ebm(add9)", "D#/Ebm6add9", "D#/Ebmmaj7", "D#/Ebmmaj9", "D#/Ebm7b5", "D#/Ebm7#5", "D#/Eb6", "D#/Eb9", "D#/Eb11", "D#/Eb13", "D#/Eb7b5", "D#/Eb7#5", "D#/Eb7b9", "D#/Eb7#9", "D#/Eb7(b5)", "D#/Eb7(#5)", "D#/Eb9b5", "D#/Eb9#5", "D#/Eb13#11", "D#/Eb13b9", "D#/Eb11b9", "D#/Ebsus2sus4", "D#/Eb-5",

        // E Chords
        "E", "Em", "E7", "E5", "Edim", "Edim7", "Eaug", "Esus2", "Esus", "Emaj7", "Em7", "E7sus4", "Emaj9", "Emaj11", "Emaj13", "Emaj9(#11)", "Emaj13(#11)", "Eadd9", "E6add9", "Emaj7(b5)", "Emaj7(#5)", "Em6", "Em9", "Em11", "Em13", "Em(add9)", "Em6add9", "Emmaj7", "Emmaj9", "Em7b5", "Em7#5", "E6", "E9", "E11", "E13", "E7b5", "E7#5", "E7b9", "E7#9", "E7(b5)", "E7(b5)", "E7(#5)", "E7(#5)", "E9b5", "E9#5", "E13#11", "E13b9", "E11b9", "Esus2sus4", "E-5",

        // F Chords
        "F", "Fm", "F7", "F5", "Fdim", "Fdim7", "Faug", "Fsus2", "Fsus", "Fmaj7", "Fm7", "F7sus4", "Fmaj9", "Fmaj11", "Fmaj13", "Fmaj9(#11)", "Fmaj13(#11)", "Fadd9", "F6add9", "Fmaj7(b5)", "Fmaj7(#5)", "Fm6", "Fm9", "Fm11", "Fm13", "Fm(add9)", "Fm6add9", "Fmmaj7", "Fmmaj9", "Fm7b5", "Fm7#5", "F6", "F9", "F11", "F13", "F7b5", "F7#5", "F7b9", "F7#9", "F7(b5)", "F7(b5)", "F7(#5)", "F7(#5)", "F9b5", "F9#5", "F13#11", "F13b9", "F11b9", "Fsus2sus4", "F-5",

        // F#/Gb Chords
        "F#/Gb", "F#/Gbm", "F#/Gb7", "F#/Gb5", "F#/Gbdim", "F#/Gbdim7", "F#/Gbaug", "F#/Gbsus2", "F#/Gbsus", "F#/Gbmaj7", "F#/Gbm7", "F#/Gb7sus4", "F#/Gbmaj9", "F#/Gbmaj11", "F#/Gbmaj13", "F#/Gbmaj9(#11)", "F#/Gbmaj13(#11)", "F#/Gbadd9", "F#/Gb6add9", "F#/Gbmaj7(b5)", "F#/Gbmaj7(#5)", "F#/Gbm6", "F#/Gbm9", "F#/Gbm11", "F#/Gbm13", "F#/Gbm(add9)", "F#/Gbm6add9", "F#/Gbmmaj7", "F#/Gbmmaj9", "F#/Gbm7b5", "F#/Gbm7#5", "F#/Gb6", "F#/Gb9", "F#/Gb11", "F#/Gb13", "F#/Gb7b5", "F#/Gb7#5", "F#/Gb7b9", "F#/Gb7#9", "F#/Gb7(b5)", "F#/Gb7(b5)", "F#/Gb7(#5)", "F#/Gb7(#5)", "F#/Gb9b5", "F#/Gb9#5", "F#/Gb13#11", "F#/Gb13b9", "F#/Gb11b9", "F#/Gbsus2sus4", "F#/Gb-5",

        // G Chords
        "G", "Gm", "G7", "G5", "Gdim", "Gdim7", "Gaug", "Gsus2", "Gsus", "Gmaj7", "Gm7", "G7sus4", "Gmaj9", "Gmaj11", "Gmaj13", "Gmaj9(#11)", "Gmaj13(#11)", "Gadd9", "G6add9", "Gmaj7(b5)", "Gmaj7(#5)", "Gm6", "Gm9", "Gm11", "Gm13", "Gm(add9)", "Gm6add9", "Gmmaj7", "Gmmaj9", "Gm7b5", "Gm7#5", "G6", "G9", "G11", "G13", "G7b5", "G7#5", "G7b9", "G7#9", "G7(b5)", "G7(b5)", "G7(#5)", "G7(#5)", "G9b5", "G9#5", "G13#11", "G13b9", "G11b9", "Gsus2sus4", "G-5",

        // G#/Ab Chords
        "G#/Ab", "G#/Abm", "G#/Ab7", "G#/Ab5", "G#/Abdim", "G#/Abdim7", "G#/Abaug", "G#/Absus2", "G#/Absus", "G#/Abmaj7", "G#/Abm7", "G#/Ab7sus4", "G#/Abmaj9", "G#/Abmaj11", "G#/Abmaj13", "G#/Abmaj9(#11)", "G#/Abmaj13(#11)", "G#/Abadd9", "G#/Ab6add9", "G#/Abmaj7(b5)", "G#/Abmaj7(#5)", "G#/Abm6", "G#/Abm9", "G#/Abm11", "G#/Abm13", "G#/Abm(add9)", "G#/Abm6add9", "G#/Abmmaj7", "G#/Abmmaj9", "G#/Abm7b5", "G#/Abm7#5", "G#/Ab6", "G#/Ab9", "G#/Ab11", "G#/Ab13", "G#/Ab7b5", "G#/Ab7#5", "G#/Ab7b9", "G#/Ab7#9", "G#/Ab7(b5)", "G#/Ab7(b5)", "G#/Ab7(#5)", "G#/Ab7(#5)", "G#/Ab9b5", "G#/Ab9#5", "G#/Ab13#11", "G#/Ab13b9", "G#/Ab11b9", "G#/Absus2sus4", "G#/Ab-5",

        // A Chords
        "A", "Am", "A7", "A5", "Adim", "Adim7", "Aaug", "Asus2", "Asus", "Amaj7", "Am7", "A7sus4", "Amaj9", "Amaj11", "Amaj13", "Amaj9(#11)", "Amaj13(#11)", "Aadd9", "A6add9", "Amaj7(b5)", "Amaj7(#5)", "Am6", "Am9", "Am11", "Am13", "Am(add9)", "Am6add9", "Ammaj7", "Ammaj9", "Am7b5", "Am7#5", "A6", "A9", "A11", "A13", "A7b5", "A7#5", "A7b9", "A7#9", "A7(b5)", "A7(b5)", "A7(#5)", "A7(#5)", "A9b5", "A9#5", "A13#11", "A13b9",

        // A#/Bb Chords
        "A#/Bb", "A#/Bbm", "A#/Bb7", "A#/Bb5", "A#/Bbdim", "A#/Bbdim7", "A#/Bbaug", "A#/Bbsus2", "A#/Bbsus", "A#/Bbmaj7", "A#/Bbm7", "A#/Bb7sus4", "A#/Bbmaj9", "A#/Bbmaj11", "A#/Bbmaj13", "A#/Bbmaj9(#11)", "A#/Bbmaj13(#11)", "A#/Bbadd9", "A#/Bb6add9", "A#/Bbmaj7(b5)", "A#/Bbmaj7(#5)", "A#/Bbm6", "A#/Bbm9", "A#/Bbm11", "A#/Bbm13", "A#/Bbm(add9)", "A#/Bbm6add9", "A#/Bbmmaj7", "A#/Bbmmaj9", "A#/Bbm7b5", "A#/Bbm7#5", "A#/Bb6", "A#/Bb9", "A#/Bb11", "A#/Bb13", "A#/Bb7b5", "A#/Bb7#5", "A#/Bb7b9", "A#/Bb7#9", "A#/Bb7(b5)", "A#/Bb7(b5)", "A#/Bb7(#5)", "A#/Bb7(#5)", "A#/Bb9b5", "A#/Bb9#5", "A#/Bb13#11", "A#/Bb13b9", "A#/Bb11b9", "A#/Bbsus2sus4", "A#/Bb-5",

        // B Chords
        "B", "Bm", "B7", "B5", "Bdim", "Bdim7", "Baug", "Bsus2", "Bsus", "Bmaj7", "Bm7", "B7sus4", "Bmaj9", "Bmaj11", "Bmaj13", "Bmaj9(#11)", "Bmaj13(#11)", "Badd9", "B6add9", "Bmaj7(b5)", "Bmaj7(#5)", "Bm6", "Bm9", "Bm11", "Bm13", "Bm(add9)", "Bm6add9", "Bmmaj7", "Bmmaj9", "Bm7b5", "Bm7#5", "B6", "B9", "B11", "B13", "B7b5", "B7#5", "B7b9", "B7#9", "B7(b5)", "B7(b5)", "B7(#5)", "B7(#5)", "B9b5", "B9#5", "B13#11", "B13b9", "B11b9", "Bsus2sus4", "B-5",

        "D#m", "D#7", "D#m7", "D#7sus4", "D#maj9", "D#maj11", "D#maj13", "D#maj9(#11)", "D#maj13(#11)", "D#add9", "D#6add9", "D#maj7(b5)", "D#maj7(#5)", "D#m6", "D#m9", "D#m11", "D#m13", "D#m(add9)", "D#m6add9", "D#mmaj7", "D#mmaj9", "D#m7b5", "D#m7#5", "D#6", "D#9", "D#11", "D#13", "D#7b5", "D#7#5", "D#7b9", "D#7#9", "D#7(b5)", "D#7(#5)", "D#9b5", "D#9#5", "D#13#11", "D#13b9", "D#11b9", "D#sus2sus4", "D#-5",

        // F# Chords
        "F#m", "F#7", "F#m7", "F#7sus4", "F#maj9", "F#maj11", "F#maj13", "F#maj9(#11)", "F#maj13(#11)", "F#add9", "F#6add9", "F#maj7(b5)", "F#maj7(#5)", "F#m6", "F#m9", "F#m11", "F#m13", "F#m(add9)", "F#m6add9", "F#mmaj7", "F#mmaj9", "F#m7b5", "F#m7#5", "F#6", "F#9", "F#11", "F#13", "F#7b5", "F#7#5", "F#7b9", "F#7#9", "F#7(b5)", "F#7(#5)", "F#9b5", "F#9#5", "F#13#11", "F#13b9", "F#11b9", "F#sus2sus4", "F#-5",

        // G# Chords
        "G#m", "G#7", "G#m7", "G#7sus4", "G#maj9", "G#maj11", "G#maj13", "G#maj9(#11)", "G#maj13(#11)", "G#add9", "G#6add9", "G#maj7(b5)", "G#maj7(#5)", "G#m6", "G#m9", "G#m11", "G#m13", "G#m(add9)", "G#m6add9", "G#mmaj7", "G#mmaj9", "G#m7b5", "G#m7#5", "G#6", "G#9", "G#11", "G#13", "G#7b5", "G#7#5", "G#7b9", "G#7#9", "G#7(b5)", "G#7(#5)", "G#9b5", "G#9#5", "G#13#11", "G#13b9", "G#11b9", "G#sus2sus4", "G#-5",

        // A# Chords
        "A#m", "A#7", "A#m7", "A#7sus4", "A#maj9", "A#maj11", "A#maj13", "A#maj9(#11)", "A#maj13(#11)", "A#add9", "A#6add9", "A#maj7(b5)", "A#maj7(#5)", "A#m6", "A#m9", "A#m11", "A#m13", "A#m(add9)", "A#m6add9", "A#mmaj7", "A#mmaj9", "A#m7b5", "A#m7#5", "A#6", "A#9", "A#11", "A#13", "A#7b5", "A#7#5", "A#7b9", "A#7#9", "A#7(b5)", "A#7(#5)", "A#9b5", "A#9#5", "A#13#11", "A#13b9", "A#11b9", "A#sus2sus4", "A#-5",

        // Cb Chords
        "Cbm", "Cbmaj7", "Cbmaj9", "Cbmaj11", "Cbmaj13", "Cbmaj9(#11)", "Cbmaj13(#11)", "Cbm7", "Cbm9", "Cbm11", "Cbm13", "Cbmmaj7", "Cbmmaj9", "Cbm7b5", "Cbm7#5", "Cbm6", "Cbm6add9",

        // Db Chords
        "Dbm", "Dbmaj7", "Dbmaj9", "Dbmaj11", "Dbmaj13", "Dbmaj9(#11)", "Dbmaj13(#11)", "Dbm7", "Dbm9", "Dbm11", "Dbm13", "Dbmmaj7", "Dbmmaj9", "Dbm7b5", "Dbm7#5", "Dbm6", "Dbm6add9", "Dbm6add9", "Dbsus2", "Dbsus4", "Dbmadd9", "Dbm6add9", "Dbm9", "Dbm11", "Dbm13", "Dbmaj7b5", "Dbmaj7#5", "Dbmaj7#11", "Dbmaj9b5", "Dbmaj9#5", "Dbmaj11b9", "Dbmaj13b9", "Dbmaj13#11", "Dbsus2sus4", "Db-5",

        // Eb Chords
        "Ebm", "Ebmaj7", "Ebmaj9", "Ebmaj11", "Ebmaj13", "Ebmaj9(#11)", "Ebmaj13(#11)", "Ebm7", "Ebm9", "Ebm11", "Ebm13", "Ebmmaj7", "Ebmmaj9", "Ebm7b5", "Ebm7#5", "Ebm6", "Ebm6add9", "Ebm6add9", "Ebsus2", "Ebsus4", "Ebmadd9", "Ebm6add9", "Ebm9", "Ebm11", "Ebm13", "Ebmaj7b5", "Ebmaj7#5", "Ebmaj7#11", "Ebmaj9b5", "Ebmaj9#5", "Ebmaj11b9", "Ebmaj13b9", "Ebmaj13#11", "Ebsus2sus4", "Eb-5",

        // Gb Chords
        "Gbm", "Gbmaj7", "Gbmaj9", "Gbmaj11", "Gbmaj13", "Gbmaj9(#11)", "Gbmaj13(#11)", "Gbm7", "Gbm9", "Gbm11", "Gbm13", "Gbmmaj7", "Gbmmaj9", "Gbm7b5", "Gbm7#5", "Gbm6", "Gbm6add9", "Gbm6add9", "Gbsus2", "Gbsus4", "Gbmadd9", "Gbm6add9", "Gbm9", "Gbm11", "Gbm13", "Gbmaj7b5", "Gbmaj7#5", "Gbmaj7#11", "Gbmaj9b5", "Gbmaj9#5", "Gbmaj11b9", "Gbmaj13b9", "Gbmaj13#11", "Gbsus2sus4", "Gb-5",

        // Ab Chords
        "Abm", "Abmaj7", "Abmaj9", "Abmaj11", "Abmaj13", "Abmaj9(#11)", "Abmaj13(#11)", "Abm7", "Abm9", "Abm11", "Abm13", "Abmmaj7", "Abmmaj9", "Abm7b5", "Abm7#5", "Abm6", "Abm6add9", "Abm6add9", "Absus2", "Absus4", "Abmadd9", "Abm6add9", "Abm9", "Abm11", "Abm13", "Abmaj7b5", "Abmaj7#5", "Abmaj7#11", "Abmaj9b5", "Abmaj9#5", "Abmaj11b9", "Abmaj13b9", "Abmaj13#11", "Absus2sus4", "Ab-5",

        // Bb Chords
        "Bbm", "Bbmaj7", "Bbmaj9", "Bbmaj11", "Bbmaj13", "Bbmaj9(#11)", "Bbmaj13(#11)", "Bbm7", "Bbm9", "Bbm11", "Bbm13", "Bbmmaj7", "Bbmmaj9", "Bbm7b5", "Bbm7#5", "Bbm6", "Bbm6add9", "Bbm6add9", "Bbsus2", "Bbsus4", "Bbmadd9", "Bbm6add9", "Bbm9", "Bbm11", "Bbm13", "Bbmaj7b5", "Bbmaj7#5", "Bbmaj7#11", "Bbmaj9b5", "Bbmaj9#5", "Bbmaj11b9", "Bbmaj13b9", "Bbmaj13#11", "Bbsus2sus4", "Bb-5",

        // B Chords
        "Bbm", "Bbmaj7", "Bbmaj9", "Bbmaj11", "Bbmaj13", "Bbmaj9(#11)", "Bbmaj13(#11)", "Bbm7", "Bbm9", "Bbm11", "Bbm13", "Bbmmaj7", "Bbmmaj9", "Bbm7b5", "Bbm7#5", "Bbm6", "Bbm6add9", "Bbm6add9", "Bbsus2", "Bbsus4", "Bbmadd9", "Bbm6add9", "Bbm9", "Bbm11", "Bbm13", "Bbmaj7b5", "Bbmaj7#5", "Bbmaj7#11", "Bbmaj9b5", "Bbmaj9#5", "Bbmaj11b9", "Bbmaj13b9", "Bbmaj13#11", "Bbsus2sus4", "Bb-5"
    )


    fun findChords(chordsLines: List<String>): ArrayList<String> {
        val chords = arrayListOf<String>()

        for (chordLine in chordsLines) {

            for (musicChord in musicChords){

                if (chordLine.contains(" $musicChord ")){
                    if (!chords.contains(musicChord)){
                        chords.add(musicChord)
                    }
                }
            }
        }

        return chords
    }

    fun isItAChordsLine(songLine:String):Boolean{

        for(musicChord in musicChords){

            if(songLine.contains(" $musicChord ")){

                return true
            }

        }
     return false
    }

    fun isItDescriptionLine(songLine: String):Boolean{

        for(lineDescription in lineDescriptions){

            if(songLine.contains(lineDescription, ignoreCase = true)){

                return true
            }
        }
        return false
}
}