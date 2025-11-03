package com.rony.assignment.features.notes.domain

import com.rony.assignment.core.domain.notes.Note
import com.rony.assignment.features.notes.domain.NoteUi
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<Note>>
    fun getNote(id: Int): Flow<Note>
    suspend fun deleteNote(note: Note)
    suspend fun saveNotes(notes: List<Note>)
    suspend fun saveNote(note: Note)
}