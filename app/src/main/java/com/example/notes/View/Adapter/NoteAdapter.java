package com.example.notes.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notes.Model.Note;
import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private List<Note> noteList = new ArrayList<>();


    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View noteView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_single_layout, parent, false);

        return new NoteViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note currentNote = noteList.get(position);

        holder.titleField.setText(currentNote.getTitle());
        holder.contentField.setText(currentNote.getDescription());
        holder.priorityField.setText( String.valueOf(currentNote.getPriority()) );

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<Note> notes){
        this.noteList = notes;
        notifyDataSetChanged();
    }


    public Note getNote(int position){
        return noteList.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private CardView noteCardView;
        private TextView titleField, contentField, priorityField;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            bindUI(itemView);
        }

        private void bindUI(View itemView){
            noteCardView = itemView.findViewById(R.id.noteLayout);
            titleField = itemView.findViewById(R.id.noteTitleID);
            contentField = itemView.findViewById(R.id.noteContentID);
            priorityField = itemView.findViewById(R.id.notePriorityID);
        }

    }


}
