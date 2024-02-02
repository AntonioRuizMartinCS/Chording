package com.example.dp.objects

class ChordsFinder(
    val songLines:List<String>
) {

    private val musicChords: ArrayList<String> = arrayListOf(
        // C Chords
        "C", "Cm", "C7", "C5", "Cdim", "Cdim7", "Caug", "Csus2", "Csus", "Cmaj7", "Cm7", "C7sus4", "Cmaj9", "Cmaj11", "Cmaj13", "Cmaj9(#11)", "Cmaj13(#11)", "Cadd9", "C6add9", "Cmaj7(b5)", "Cmaj7(#5)", "Cm6", "Cm9", "Cm11", "Cm13", "Cm(add9)", "Cm6add9", "Cmmaj7", "Cmmaj9", "Cm7b5", "Cm7#5", "C6", "C9", "C11", "C13", "C7b5", "C7#5", "C7b9", "C7#9", "C7(b5)", "C7(#5)", "C9b5", "C9#5", "C13#11", "C13b9", "C11b9", "Csus2sus4", "C-5",

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
        "B", "Bm", "B7", "B5", "Bdim", "Bdim7", "Baug", "Bsus2", "Bsus", "Bmaj7", "Bm7", "B7sus4", "Bmaj9", "Bmaj11", "Bmaj13", "Bmaj9(#11)", "Bmaj13(#11)", "Badd9", "B6add9", "Bmaj7(b5)", "Bmaj7(#5)", "Bm6", "Bm9", "Bm11", "Bm13", "Bm(add9)", "Bm6add9", "Bmmaj7", "Bmmaj9", "Bm7b5", "Bm7#5", "B6", "B9", "B11", "B13", "B7b5", "B7#5", "B7b9", "B7#9", "B7(b5)", "B7(b5)", "B7(#5)", "B7(#5)", "B9b5", "B9#5", "B13#11", "B13b9", "B11b9", "Bsus2sus4", "B-5"
        )






                 fun findChords(songLines:List<String>): ArrayList<String>{

                     val songChords:ArrayList<String> = arrayListOf()


                     for(songLine in songLines){

                         for (musicChord in musicChords){

                             if (songLine.contains(other = " $musicChord ", ignoreCase = false) || songLine.contains(other = " $musicChord", ignoreCase = false)
                                 || songLine.contains(other = "$musicChord ", ignoreCase = false)) {

                                 if(!songChords.contains(musicChord)){
                                     songChords.add(musicChord)
                                 }

                             }

                         }
                        }

                    return songChords
                }

}