package com.washfi.todoapp.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface NotesDao {
    @Query("SELECT * from notesData")
    fun getAll(): List<Note>

    @Insert(onConflict = REPLACE)
    fun insert(note: Note)

    @Update
    fun updateNotes(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("DELETE FROM notesData WHERE isTaskCompleted= :status")
    fun deleteNote(status: Boolean)
}