package com.example.notes.Model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class} , version = 1)
public abstract class NoteDb extends RoomDatabase {

    public static final String DB_TABLE_NAME = "note_database";

    private static NoteDb instance; //needed to create singleton

    public abstract NoteDao noteDao();  //used in repository to access the database operations (delete, insert, update, so on)

    //this is the one in charge of creating the database
    public static synchronized  NoteDb getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDb.class, DB_TABLE_NAME )
                         .fallbackToDestructiveMigration()
                         .build();
        }
        return instance;
    }

}
