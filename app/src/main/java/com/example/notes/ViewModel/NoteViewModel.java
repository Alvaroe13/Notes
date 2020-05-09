package com.example.notes.ViewModel;

import android.app.Application;

import com.example.notes.Model.Note;
import com.example.notes.Model.NoteRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repositoryAccess;
    private LiveData<List<Note>> noteList;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repositoryAccess = new NoteRepository(application);
        noteList = repositoryAccess.getExistingNote();
    }

    public void insertNote(Note note){
        repositoryAccess.insert(note);
    }

    public void updateNote(Note note){
        repositoryAccess.update(note);
    }

    public void deleteNote(Note note){
        repositoryAccess.delete(note);
    }

    public void deleteAllNotes(){
        repositoryAccess.deleteEveryNote();
    }

    /*Here We retrieve the info coming from the "NoteRepository" class and we pass it onto
      the MainActivity*/
    public LiveData<List<Note>> getAllNotes(){
        return noteList;
    }

}
