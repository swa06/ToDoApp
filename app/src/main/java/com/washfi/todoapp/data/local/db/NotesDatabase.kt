package com.washfi.todoapp.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    //companion works like static in Java
    companion object {
        lateinit var INSTANCE: NotesDatabase
        fun getInstance(context: Context): NotesDatabase {
            synchronized(NotesDatabase::class) {
                INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NotesDatabase::class.java, "my-notes.db")
                        .allowMainThreadQueries()
                        .build()
            }

            return INSTANCE
        }
    }
}