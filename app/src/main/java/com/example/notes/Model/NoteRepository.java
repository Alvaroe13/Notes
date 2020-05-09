package com.example.notes.Model;

import android.app.Application;
import android.os.AsyncTask;

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
             /* This are the methods to be passed onto viewModel and later on MainActivity*/

    public void insert(Note note){
        //instantiate asyncTask class
        new insertNoteAsyncTask(noteDao).execute(note);
    }
    public void update(Note note){
        new updateAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note){
        new deleteAsyncTask(noteDao).execute(note);
    }
    public void deleteEveryNote(){
        new deleteAllNotesAsyncTask(noteDao).execute();
    }

    /* Here we retrieve the notes coming from the DAO and we will pass it onto
       the NoteViewModel */
    public  LiveData<List<Note>> getExistingNote(){
        return noteList;
    }

    public static class insertNoteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao dao;

        //constructor to be able to instantiate in the parent class
        public insertNoteAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        //here lies the logic to operate, as requested by Room library it must be done in a background thread
        @Override
        protected Void doInBackground(Note... notes) {
            dao.insert(notes[0]);
            return null;
        }
    }
    public static class updateAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao dao;

        //constructor to be able to instantiate in the parent class


        public updateAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        //here lies the logic to operate, as requested by Room library it must be done in a background thread
        @Override
        protected Void doInBackground(Note... notes) {
            dao.update(notes[0]);
            return null;
        }
    }
    public static class deleteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao dao;

        //constructor to be able to instantiate in the parent class
        public deleteAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        //here lies the logic to operate, as requested by Room library it must be done in a background thread
        @Override
        protected Void doInBackground(Note... notes) {
            dao.insert(notes[0]);
            return null;
        }
    }
    public static class deleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDao dao;

        //constructor to be able to instantiate in the parent class
        public deleteAllNotesAsyncTask(NoteDao dao) {
            this.dao = dao;
        }

        //here lies the logic to operate, as requested by Room library it must be done in a background thread
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteEveryNote();
            return null;
        }
    }


}
