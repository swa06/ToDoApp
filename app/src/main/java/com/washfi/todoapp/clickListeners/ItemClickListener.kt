package com.washfi.todoapp.clickListeners

import com.washfi.todoapp.db.Note

interface ItemClickListener {
    fun onClick(note: Note)
    fun onUpdate(note: Note)
}