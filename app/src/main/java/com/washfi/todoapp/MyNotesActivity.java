package com.washfi.todoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.washfi.todoapp.adapter.NoteAdapter;
import com.washfi.todoapp.clickListeners.ItemClickListener;
import com.washfi.todoapp.model.Note;

import java.util.ArrayList;

import static com.washfi.todoapp.AppConstant.DESCRIPTION;
import static com.washfi.todoapp.AppConstant.TITLE;

public class MyNotesActivity extends AppCompatActivity {

    String fullName;
    FloatingActionButton fabAddNotes;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerViewNotes;
    ArrayList<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        bindView();
        setUpSharedPreferences();
        getIntentData();
        notes = new ArrayList<>();
        fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpDialogBox();
            }
        });

        getSupportActionBar().setTitle(fullName);
    }

    private void setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstant.FULL_NAME);
        if (TextUtils.isEmpty(fullName)) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "");
        }
    }

    private void bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }

    private void setUpDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout, null);
        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDescription = view.findViewById(R.id.editTextDescription);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
                    Note note = new Note();
                    note.setTitle(title);
                    note.setDescription(description);
                    notes.add(note);
                } else {
                    Toast.makeText(MyNotesActivity.this, "Title or description can't " +
                            "be empty", Toast.LENGTH_SHORT).show();
                }
                setUpRecyclerView();
                dialog.hide();
            }
        });
        dialog.show();
    }

    private void setUpRecyclerView() {
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Note note) {
                Intent intent = new Intent(MyNotesActivity.this, DetailActivity.class);
                intent.putExtra(TITLE, note.getTitle());
                intent.putExtra(DESCRIPTION, note.getDescription());
                startActivity(intent);
            }
        };

        NoteAdapter noteAdapter = new NoteAdapter(notes, itemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(noteAdapter);
    }
}