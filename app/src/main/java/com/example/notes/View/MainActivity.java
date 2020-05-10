package com.example.notes.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.notes.Model.Note;
import com.example.notes.Model.NoteRepository;
import com.example.notes.R;
import com.example.notes.View.Adapter.NoteAdapter;
import com.example.notes.ViewModel.NoteViewModel;
import com.example.notes.ViewModel.NoteViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity {


     private NoteViewModel noteViewModel;
     private RecyclerView recyclerView;
     private  NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Recycler();
        initViewModel();

    }

    private void Recycler(){

        recyclerView = findViewById(R.id.noteRecyclerID);
        adapter = new NoteAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    /**
     * here we init the viewModel and fetch existing notes in the local db if there's any
     */
    public void initViewModel(){

        noteViewModel = new ViewModelProvider(this, new NoteViewModelFactory(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                //here we update info and set it onto te recyclerView
                adapter.setNotes(notes);
            }
        });

    }
}
