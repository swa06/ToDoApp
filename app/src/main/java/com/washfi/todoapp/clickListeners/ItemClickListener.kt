package com.washfi.todoapp.clickListeners

import com.washfi.todoapp.model.Note

interface ItemClickListener {
    fun onClick(note: Note)
}