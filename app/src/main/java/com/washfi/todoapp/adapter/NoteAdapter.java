package com.washfi.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.washfi.todoapp.R;
import com.washfi.todoapp.clickListeners.ItemClickListener;
import com.washfi.todoapp.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note> noteList;
    private ItemClickListener itemClickListener;

    public NoteAdapter(List<Note> noteList, ItemClickListener itemClickListener) {
        this.noteList = noteList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_adapter_layout,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        final Note note = noteList.get(position);
        String title = note.getTitle();
        String description = note.getDescription();
        holder.textViewTitle.setText(title);
        holder.textViewDescription.setText(description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               itemClickListener.onClick(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
