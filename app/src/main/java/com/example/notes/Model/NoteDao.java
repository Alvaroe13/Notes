package com.example.notes.Model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao    //Data access object
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteEveryNote();

    //this method will retrieve all the existing notes to set it up into the recyclerView
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getEveryNote();


}
