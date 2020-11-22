package com.washfi.todoapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.washfi.todoapp.AppConstant.DESCRIPTION
import com.washfi.todoapp.AppConstant.TITLE
import com.washfi.todoapp.adapter.NoteAdapter
import com.washfi.todoapp.clickListeners.ItemClickListener
import com.washfi.todoapp.model.Note

class MyNotesActivity : AppCompatActivity() {
    var fullName: String = ""
    lateinit var fabAddNotes: FloatingActionButton
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerViewNotes: RecyclerView
    lateinit var notes: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)
        bindView()
        setUpSharedPreferences()
        getIntentData()
        notes = ArrayList()
        supportActionBar?.title = fullName
        fabAddNotes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                setUpDialog()
            }

        })
    }

    private fun setUpDialog() {
        val view = LayoutInflater.from(this@MyNotesActivity).inflate(R.layout.add_notes_dialog_layout, null)
        val editTextTitle = view.findViewById<EditText>(R.id.editTextTitle)
        val editTextDescription = view.findViewById<EditText>(R.id.editTextDescription)
        val buttonSubmit = view.findViewById<Button>(R.id.buttonSubmit)
        val dialog = AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create()
        buttonSubmit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val title = editTextTitle.text.toString()
                val description = editTextDescription.text.toString()
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    val note = Note(title, description)
                    notes.add(note)
                } else {
                    Toast.makeText(this@MyNotesActivity, "Title or description can't " +
                            "be empty", Toast.LENGTH_SHORT).show()
                }
                setUpRecyclerView()
                dialog.hide()
            }
        })
        dialog.show()
    }

    private fun setUpRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onClick(note: Note) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(TITLE, note.title)
                intent.putExtra(DESCRIPTION, note.description)
                startActivity(intent)
            }

        }
        val noteAdapter = NoteAdapter(notes, itemClickListener)
        val linearLayoutManager = LinearLayoutManager(this@MyNotesActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recyclerViewNotes.layoutManager = linearLayoutManager
        recyclerViewNotes.adapter = noteAdapter
    }

    private fun bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes)
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes)
    }

    private fun setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun getIntentData() {
        val intent = intent
        if (intent.hasExtra(AppConstant.FULL_NAME)){
            fullName = intent.getStringExtra(AppConstant.FULL_NAME).toString()
        }

        if (fullName.isEmpty()) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "").toString()
        }
    }
}