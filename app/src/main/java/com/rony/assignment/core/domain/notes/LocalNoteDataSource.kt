package com.rony.assignment.core.domain.notes

import com.rony.assignment.core.data.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

interface LocalNoteDataSource {
    fun getAllNotes(): Flow<List<Note>>
    fun getNote(id: Int): Flow<Note>
    suspend fun saveNote(note: Note)
    suspend fun saveNotes(notes: List<Note>)
    suspend fun deleteNote(note: Note)
}