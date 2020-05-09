package com.example.notes.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.notes.Model.Note;
import com.example.notes.Model.NoteRepository;
import com.example.notes.R;
import com.example.notes.ViewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewModel();

    }

    /**
     * here we init the viewModel and fetch existing notes in the local db if there's any
     */
    public void initViewModel(){

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                //here we update info and set it onto te recyclerView
            }
        });

    }
}
