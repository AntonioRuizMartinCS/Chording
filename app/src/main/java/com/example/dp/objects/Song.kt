package com.example.dp.objects

class Song(

    var id:Int,
    var songName:String,
    var artist:String,
    var songBody:String,
    var capo:String,
    var tuning:String,
    var key:String,
    var songChords:ArrayList<String>,
    var minutes:Int,
    var seconds:Int
): java.io.Serializable {

}