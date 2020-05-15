package com.example.notes.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notes.Model.Note;
import com.example.notes.R;
import com.example.notes.View.Adapter.NoteAdapter;
import com.example.notes.ViewModel.NoteViewModel;
import com.example.notes.ViewModel.NoteViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 100;
    public static final int EDIT_NOTE_REQUEST = 200;


    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    //ui
    private FloatingActionButton addNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        Recycler();
        initViewModel();
        fabButton();
        swipeToDelete();
        noteClicked();


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
        startActivityForResult(i, ADD_NOTE_REQUEST);
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.noteToolbarTitle);
        setSupportActionBar(toolbar);
        setTitle("Notes");
    }

    private void Recycler() {

        recyclerView = findViewById(R.id.noteRecyclerID);
        adapter = new NoteAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    /**
     * this method have the logic when note layout is clicked
     */
    private void noteClicked() {
        adapter.setOnNoteClickListener(new NoteAdapter.ClickListener() {
            @Override
            public void onNoteClick(Note note) {
                Intent intent = new Intent(MainActivity.this, NoteRoomActivity.class);
                intent.putExtra(NoteRoomActivity.NOTE_ID, note.getId());
                intent.putExtra(NoteRoomActivity.NOTE_TITLE, note.getTitle());
                intent.putExtra(NoteRoomActivity.NOTE_DESCRIPTION, note.getDescription());
                intent.putExtra(NoteRoomActivity.NOTE_PRIORITY, note.getPriority());
                startActivityForResult(intent, EDIT_NOTE_REQUEST);
            }
        });
    }


    /**
     * here we init the viewModel and fetch existing notes in the local db if there's any
     */
    public void initViewModel() {

        noteViewModel = new ViewModelProvider(this, new NoteViewModelFactory(this.getApplication())).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                //here we update info and set it onto te recyclerView
                adapter.setNotes(notes);
            }
        });

    }

    /**
     * method in charge of deleting note with swiping gesture
     */
    private void swipeToDelete() {

        //this is an Android class designed to execute something when swiped
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                noteViewModel.deleteNote(adapter.getNote(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {

            //here we retrieve info sent from noteActivity
            String title = data.getStringExtra(NoteRoomActivity.NOTE_TITLE);
            String description = data.getStringExtra(NoteRoomActivity.NOTE_DESCRIPTION);
            int priority = data.getIntExtra(NoteRoomActivity.NOTE_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insertNote(note);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {

            int id = data.getIntExtra(NoteRoomActivity.NOTE_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                return;
            }

            //here we retrieve info sent from noteActivity
            String title = data.getStringExtra(NoteRoomActivity.NOTE_TITLE);
            String description = data.getStringExtra(NoteRoomActivity.NOTE_DESCRIPTION);
            int priority = data.getIntExtra(NoteRoomActivity.NOTE_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.updateNote(note);
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "note not Saved", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                noteViewModel.deleteAllNotes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
