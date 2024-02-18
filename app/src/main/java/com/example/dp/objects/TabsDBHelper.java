package com.example.dp.objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.dp.objects.Song;

import java.util.ArrayList;
import java.util.Arrays;

public class TabsDBHelper extends SQLiteOpenHelper {

    public static final String TAB_TABLE = "TAB_TABLE";
    public static final String SETS_TABLE = "SETS_TABLE";
    public static final String COLUMN_SET_ID = "SET_ID";
    public static final String SET_NAME = "SET_NAME";

    public static final String SONG_SETS_TABLE = "SONG_SETS_TABLE";

    public static final String COLUMN_SONG_ID = "SONG_ID";
    public static final String COLUMN_SONG_NAME = "SONG_NAME";
    public static final String COLUMN_ARTIST = "ARTIST";
    public static final String COLUMN_SONG_BODY = "SONG_BODY";
    public static final String COLUMN_CAPO = "CAPO";
    public static final String COLUMN_TUNING = "TUNING";
    public static final String COLUMN_SONG_KEY = "SONG_KEY";
    public static final String COLUMN_CHORDS = "CHORDS";

    public static final String COLUMN_MINUTES = "MINUTES";

    public static final String COLUMN_SECONDS = "SECONDS";


    public TabsDBHelper(@Nullable Context context) {
        super(context, "tabs.db", null, 1);
    }

    //First time the db is created. Code to create the db
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStatement = "CREATE TABLE " + TAB_TABLE + " (" + COLUMN_SONG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SONG_NAME + " TEXT, "
                + COLUMN_ARTIST + " TEXT, "
                + COLUMN_SONG_BODY + " TEXT, "
                + COLUMN_CAPO + " TEXT, "
                + COLUMN_TUNING + " TEXT, "
                + COLUMN_SONG_KEY + " TEXT, "
                + COLUMN_CHORDS + " TEXT, "
                + COLUMN_MINUTES + " INTEGER, "
                + COLUMN_SECONDS + " INTEGER)";

        db.execSQL(createTableStatement);

        String createSecondTableStatement = "CREATE TABLE " + SETS_TABLE + " ("
                + COLUMN_SET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SET_NAME + " TEXT)";

        db.execSQL(createSecondTableStatement);



        String createJunctionTableStatement = "CREATE TABLE " + SONG_SETS_TABLE + " ("
                + COLUMN_SONG_ID + " INTEGER, "
                + COLUMN_SET_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_SONG_ID + ") REFERENCES " + TAB_TABLE + "(" + COLUMN_SONG_ID + "), "
                + "FOREIGN KEY(" + COLUMN_SET_ID + ") REFERENCES " + SETS_TABLE + "(" + COLUMN_SET_ID + "))";


        db.execSQL(createJunctionTableStatement);

    }

    //called when the db is updated. Provides forward compatibility
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void deleteTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TAB_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SETS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SONG_SETS_TABLE);

        onCreate(db);
        db.close();
    }

    public boolean deleteOneTab(Song song) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + TAB_TABLE + " WHERE " + COLUMN_SONG_ID + " = " + song.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            return true;
        } else {
            return false;
        }

    }

    public boolean deleteOneSet(Set set) {

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + SETS_TABLE + " WHERE " + COLUMN_SET_ID + " = " + set.getSetID();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            return true;
        } else {
            return false;
        }

    }


    public boolean addOneSong(Song song) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SONG_NAME, song.getSongName());
        cv.put(COLUMN_ARTIST, song.getArtist());
        cv.put(COLUMN_SONG_BODY, song.getSongBody());
        cv.put(COLUMN_CAPO, song.getCapo());
        cv.put(COLUMN_TUNING, song.getTuning());
        cv.put(COLUMN_SONG_KEY, song.getKey());
        cv.put(COLUMN_CHORDS, song.getSongChords().toString());
        cv.put(COLUMN_MINUTES, song.getMinutes());
        cv.put(COLUMN_SECONDS, song.getSeconds());


        long insert = db.insert(TAB_TABLE, null, cv);

        return insert != -1;
    }

    public boolean addSongToSet(Song song, Set set){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SONG_ID, song.getId());
        cv.put(COLUMN_SET_ID, set.getSetID());

        long insert = db.insert(SONG_SETS_TABLE, null, cv);
        return insert != -1;

    }

    public boolean addOneSet(Set set) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(SET_NAME, set.getSetName());

        long insert = db.insert(SETS_TABLE, null, cv);

        return insert != -1;
    }

    public ArrayList<Integer> getAllSongsOfSet(int setID){

        ArrayList<Integer> songsOfSet = new ArrayList<>();

        String queryString = "SELECT " + COLUMN_SONG_ID + " FROM " + SONG_SETS_TABLE +
                " WHERE " +
                 COLUMN_SET_ID + " = " + setID;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
//            loop through the cursor (result set) and create new tab objects
            do {

                int tabID = cursor.getInt(0);

                songsOfSet.add(tabID);

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return songsOfSet;

    }

    public ArrayList<Song> getAllSongsForSet(int setID){

        ArrayList<Song> songsOfSet = new ArrayList<>();

        String queryString = "SELECT t.* " +
                "FROM " + TAB_TABLE + " t " +
                "INNER JOIN " + SONG_SETS_TABLE + " ss ON t." + COLUMN_SONG_ID + " = ss." + COLUMN_SONG_ID + " " +
                "WHERE ss." + COLUMN_SET_ID + " = " + setID;


        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
//            loop through the cursor (result set) and create new tab objects
            do {

                int tabID = cursor.getInt(0);
                String songName = cursor.getString(1);
                String artist = cursor.getString(2);
                String songBody = cursor.getString(3);
                String capo = cursor.getString(4);
                String tuning = cursor.getString(5);
                String key = cursor.getString(6);
                String songChords = cursor.getString(7);
                int minutes = cursor.getInt(8);
                int seconds = cursor.getInt(9);


                String[] songChordsSplit = songChords.split(" ");

                ArrayList<String> songChordsList = new ArrayList<>(
                        Arrays.asList(songChordsSplit)
                );

                Song savedTab = new Song(tabID, songName, artist, songBody, capo, tuning, key, songChordsList, minutes, seconds);
                songsOfSet.add(savedTab);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return songsOfSet;

    }

    public ArrayList<Set> getAllSets(){

        ArrayList<Set> setsList = new ArrayList<>();
        String queryString = "SELECT * FROM " + SETS_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
//            loop through the cursor (result set) and create new tab objects
            do {

                int setID = cursor.getInt(0);
                String setName = cursor.getString(1);

                Set savedSet = new Set(setID, setName);
                setsList.add(savedSet);


            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return setsList;
    }


    public ArrayList<Song> getAllTabs() {

        ArrayList<Song> tabsList = new ArrayList<>();

        String queryString = "SELECT * FROM " + TAB_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
//            loop through the cursor (result set) and create new tab objects
            do {

                int tabID = cursor.getInt(0);
                String songName = cursor.getString(1);
                String artist = cursor.getString(2);
                String songBody = cursor.getString(3);
                String capo = cursor.getString(4);
                String tuning = cursor.getString(5);
                String key = cursor.getString(6);
                String songChords = cursor.getString(7);
                int minutes = cursor.getInt(8);
                int seconds = cursor.getInt(9);


                String[] songChordsSplit = songChords.split(" ");

                ArrayList<String> songChordsList = new ArrayList<>(
                        Arrays.asList(songChordsSplit)
                );

                Song savedTab = new Song(tabID, songName, artist, songBody, capo, tuning, key, songChordsList, minutes, seconds);
                tabsList.add(savedTab);

            } while (cursor.moveToNext());

        }

        cursor.close();
        db.close();
        return tabsList;

    }

    public void updateTable(int id, String newSongName, String newArtistName,
                            String newSongBody, String newCapo, String newTuning, String newKey, String newSongChordsList, int newMinutes, int newSeconds) {

        String query = "UPDATE " + TAB_TABLE + " SET " +
                COLUMN_SONG_NAME + " = ?, " +
                COLUMN_ARTIST + " = ?, " +
                COLUMN_SONG_BODY + " = ?, " +
                COLUMN_CAPO + " = ?, " +
                COLUMN_TUNING + " = ?, " +
                COLUMN_SONG_KEY + " = ?, " +
                COLUMN_CHORDS + " = ?, " +
                COLUMN_MINUTES + " = ?, " +
                COLUMN_SECONDS + " = ? " +
                "WHERE " + COLUMN_SONG_ID + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement stmt = db.compileStatement(query);

        stmt.bindString(1, newSongName);
        stmt.bindString(2, newArtistName);
        stmt.bindString(3, newSongBody);
        stmt.bindString(4, newCapo);
        stmt.bindString(5, newTuning);
        stmt.bindString(6, newKey);
        stmt.bindString(7, newSongChordsList);
        stmt.bindLong(8, newMinutes);
        stmt.bindLong(9, newSeconds);
        stmt.bindLong(10, id);

        stmt.executeUpdateDelete();
        db.close();

    }
}