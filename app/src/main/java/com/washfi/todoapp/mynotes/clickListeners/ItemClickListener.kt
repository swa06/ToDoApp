package com.washfi.todoapp.mynotes.clickListeners

import com.washfi.todoapp.data.local.db.Note

interface ItemClickListener {
    fun onClick(note: Note)
    fun onUpdate(note: Note)
}