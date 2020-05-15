package com.example.notes.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.notes.R;

public class NoteRoomActivity extends AppCompatActivity {

    public static final String NOTE_TITLE = "com.example.notes.View.NOTE_TITLE";
    public static final String NOTE_DESCRIPTION = "com.example.notes.View.NOTE_DESCRIPTION";
    public static final String NOTE_PRIORITY = "com.example.notes.View.NOTE_PRIORITY";;


    private EditText noteTitleField, noteDescriptionField;
    private NumberPicker notePriorityField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_room);

        bindUI();
        setToolbar();
    }

    private void bindUI() {
        noteTitleField = findViewById(R.id.noteTitleField);
        noteDescriptionField = findViewById(R.id.noteDescriptionField);
        notePriorityField = findViewById(R.id.notePriorityField);

        notePriorityField.setMinValue(1);
        notePriorityField.setMaxValue(5);
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.addNoteToolbar);
        setSupportActionBar(toolbar);
        setTitle("New Note");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.save:
                saveNoteInDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * here lies the logic to save the note
     */
    private void saveNoteInDB() {

        String noteTitle, noteDescription;
        int notePriority;

        noteTitle = noteTitleField.getText().toString();
        noteDescription = noteDescriptionField.getText().toString();
        notePriority = notePriorityField.getValue();

        //if any field empty don't save note
        if ( noteTitle.trim().isEmpty() || noteDescription.trim().isEmpty()  ){
            Toast.makeText(this, "None of the fields can by empty", Toast.LENGTH_SHORT).show();
            return;
        }

        /* NOTE ASIDE: Since this activity is used as an input layout, we won't create a connection to the db from here,
           instead we're going to pass all this info through an intent to the MainActivity were we have a connection
           to the db through the ViewModel */

        sendInfoToMainActivity( noteTitle, noteDescription, notePriority);
    }

    /**
     * lets send info through intent extras to main activity
     */
    private void sendInfoToMainActivity(String noteTitle, String noteDescription, int notePriority) {

        Intent intentData = new Intent(this, MainActivity.class);
        intentData.putExtra(NOTE_TITLE, noteTitle);
        intentData.putExtra(NOTE_DESCRIPTION, noteDescription);
        intentData.putExtra(NOTE_PRIORITY, notePriority);

        setResult(RESULT_OK, intentData);
        finish();
    }


}
