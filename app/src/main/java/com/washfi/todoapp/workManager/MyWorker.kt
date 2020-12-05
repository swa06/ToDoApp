package com.washfi.todoapp.workManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.washfi.todoapp.NotesApp

class MyWorker(val context: Context, val workerParameters: WorkerParameters) :
        Worker(context, workerParameters) {

    override fun doWork(): Result {
        val notesApp = applicationContext as NotesApp
        val notesDao = notesApp.getNotesDb().notesDao()
        notesDao.deleteNote(true)
        return Result.success()
    }
}