package com.example.notes.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.notes.Model.Note;
import com.example.notes.Model.NoteRepository;
import com.example.notes.R;
import com.example.notes.View.Adapter.NoteAdapter;
import com.example.notes.ViewModel.NoteViewModel;
import com.example.notes.ViewModel.NoteViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int NOTE_INFORMATION = 100;


     private NoteViewModel noteViewModel;
     private RecyclerView recyclerView;
     private NoteAdapter adapter;
     //ui
    private FloatingActionButton addNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteButton = findViewById(R.id.addNoteButton);

        setToolbar();
        Recycler();
        initViewModel();
        fabButton();



    }

    private void fabButton() {
        addNoteButton = findViewById(R.id.addNoteButton);
        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNoteRoom();
            }
        });
    }

    private void goToNoteRoom() {
        Intent i = new Intent(this, NoteRoomActivity.class);
        startActivityForResult( i , NOTE_INFORMATION);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.noteToolbarTitle);
        setSupportActionBar(toolbar);
        setTitle("Notes");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == NOTE_INFORMATION && resultCode == RESULT_OK){
            
            //here we retrieve info sent from noteActivity
            String title = data.getStringExtra(NoteRoomActivity.NOTE_TITLE);
            String description = data.getStringExtra(NoteRoomActivity.NOTE_DESCRIPTION);
            int priority = data.getIntExtra(NoteRoomActivity.NOTE_PRIORITY, 1);
            
            Note note = new Note(title, description, priority);
            noteViewModel.insertNote(note);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();            
        } else {
            Toast.makeText(this, "note not Saved", Toast.LENGTH_SHORT).show();
        }
 
    }
}
