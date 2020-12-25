package com.washfi.todoapp

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.washfi.todoapp.data.local.db.NotesDatabase

class NotesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }

    fun getNotesDb(): NotesDatabase {
        return NotesDatabase.getInstance(this)
    }
}