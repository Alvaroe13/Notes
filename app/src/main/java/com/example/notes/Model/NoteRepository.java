package com.example.notes.Model;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> noteList;

    //constructor to init
    public NoteRepository(Application application){
        NoteDb database = NoteDb.getInstance(application.getApplicationContext());
        noteDao = database.noteDao();
        noteList = noteDao.getEveryNote();
    }

    public void insert(Note note){

    }
    public void update(Note note){

    }
    public void delete(Note note){

    }
    public void deleteEveryNote(Note note){

    }

    public  LiveData<List<Note>> getEveryNote(){
        return noteList;
    }
}
