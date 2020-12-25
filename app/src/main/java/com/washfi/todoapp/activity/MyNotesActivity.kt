package com.washfi.todoapp.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.washfi.todoapp.NotesApp
import com.washfi.todoapp.R
import com.washfi.todoapp.adapter.NoteAdapter
import com.washfi.todoapp.clickListeners.ItemClickListener
import com.washfi.todoapp.data.local.db.Note
import com.washfi.todoapp.utils.AppConstant
import com.washfi.todoapp.utils.AppConstant.DESCRIPTION
import com.washfi.todoapp.utils.AppConstant.IMAGE_PATH
import com.washfi.todoapp.utils.AppConstant.TITLE
import com.washfi.todoapp.data.local.pref.PrefConstant
import com.washfi.todoapp.utils.workManager.MyWorker
import java.util.concurrent.TimeUnit

class MyNotesActivity : AppCompatActivity() {

    companion object {
        const val ADD_NOTE_CODE = 100
    }

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
        getDataFromDataBase()
        supportActionBar?.title = fullName
        fabAddNotes.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(this@MyNotesActivity, AddNotesActivity::class.java)
                startActivityForResult(intent, ADD_NOTE_CODE)
            }

        })
        setUpRecyclerView()
        setUpWorkManager()
    }

    private fun setUpWorkManager() {
        val constraint = Constraints.Builder()
                .build()
        val request = PeriodicWorkRequest.Builder(MyWorker::class.java, 1, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()
        WorkManager.getInstance(this).enqueue(request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_NOTE_CODE && resultCode == Activity.RESULT_OK) {
            val title = data?.getStringExtra(TITLE)
            val description = data?.getStringExtra(DESCRIPTION)
            val imagePath = data?.getStringExtra(IMAGE_PATH)
            val note = Note(title = title!!, description = description!!, imagePath = imagePath!!)
            notes.add(note)
            addNotesToDb(note)
            recyclerViewNotes.adapter?.notifyItemChanged(notes.size - 1)
        }
    }

    private fun getDataFromDataBase() {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notes.addAll(notesDao.getAll())
    }

/*    private fun setUpDialog() {
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
                    val note = Note(title = title, description = description)
                    notes.add(note)
                    addNotesToDb(note)
                } else {
                    Toast.makeText(this@MyNotesActivity, "Title or description can't " +
                            "be empty", Toast.LENGTH_SHORT).show()
                }
                dialog.hide()
            }
        })
        dialog.show()
    }*/

    private fun addNotesToDb(note: Note) {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.insert(note)
    }

    private fun setUpRecyclerView() {
        val itemClickListener = object : ItemClickListener {
            override fun onClick(note: Note) {
                val intent = Intent(this@MyNotesActivity, DetailActivity::class.java)
                intent.putExtra(TITLE, note.title)
                intent.putExtra(DESCRIPTION, note.description)
                startActivity(intent)
            }

            override fun onUpdate(note: Note) {
                val notesApp = applicationContext as NotesApp
                val notesDao = notesApp.getNotesDb().notesDao()
                notesDao.updateNotes(note)
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
        if (intent.hasExtra(AppConstant.FULL_NAME)) {
            fullName = intent.getStringExtra(AppConstant.FULL_NAME).toString()
        }

        if (fullName.isEmpty()) {
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME, "").toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.blog){
            val intent = Intent(this@MyNotesActivity, BlogActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}