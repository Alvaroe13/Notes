package com.example.notes.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class} , version = 1)
public abstract class NoteDb extends RoomDatabase {

    public static final String DB_TABLE_NAME = "note_database";

    private static NoteDb instance; //needed to create singleton

    //used in repository to access the database operations ( delete, insert, update, deleteAll, getEveryNote()  )
    public abstract NoteDao noteDao();

    //this is the one in charge of creating the database
    public static synchronized  NoteDb getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NoteDb.class, DB_TABLE_NAME )
                         .fallbackToDestructiveMigration()
                         .addCallback(dbCallback)   //here we retrieve info from db
                         .build();
        }
        return instance;
    }

    // this is the one in charge of retrieving the existing notes
    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new fetchExistingNotesAsyncTask(instance).execute();
        }

    };

    public static class fetchExistingNotesAsyncTask extends AsyncTask< Void, Void, Void >{
        NoteDao daoAccess;

        public fetchExistingNotesAsyncTask(NoteDb database) {
            this.daoAccess = database.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            daoAccess.insert( new Note("Title 1", "Description 1", 1) );
            daoAccess.insert( new Note("Title 2", "Description 2", 2) );
            daoAccess.insert( new Note("Title 3", "Description 3", 3) );
            return null;
        }
    }

}
