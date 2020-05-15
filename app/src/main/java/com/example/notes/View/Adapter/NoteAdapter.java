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
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> noteList = new ArrayList<>();
    private ClickListener listener;

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
        holder.priorityField.setText(String.valueOf(currentNote.getPriority()));

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void setNotes(List<Note> notes) {
        this.noteList = notes;
        notifyDataSetChanged();
    }


    public Note getNote(int position) {
        return noteList.get(position);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        private TextView titleField, contentField, priorityField;


        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);
            bindUI(itemView);

            //itemView clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onNoteClick(noteList.get(position));
                    }


                }
            });
        }

        private void bindUI(View itemView) {
            titleField = itemView.findViewById(R.id.noteTitleID);
            contentField = itemView.findViewById(R.id.noteContentID);
            priorityField = itemView.findViewById(R.id.notePriorityID);
        }


    }

    /**
     * Interface in charge of make the notes in Main activity clickable
     */
    public interface ClickListener {
        void onNoteClick(Note note);
    }


    public void setOnNoteClickListener(ClickListener listener) {
        this.listener = listener;

    }


}
