package com.rony.assignment.core.data.database

import com.rony.assignment.core.data.database.dao.NotesDao
import com.rony.assignment.core.data.database.entity.NoteEntity
import com.rony.assignment.core.data.database.mappers.toNote
import com.rony.assignment.core.data.database.mappers.toNoteEntity
import com.rony.assignment.core.domain.notes.LocalNoteDataSource
import com.rony.assignment.core.domain.notes.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalNoteDataSource(
    private val notesDao: NotesDao
): LocalNoteDataSource {

    override fun getAllNotes(): Flow<List<Note>> {
        return notesDao
            .getAllNotes()
            .map { notes ->
                notes.map { noteEntity ->
                    noteEntity.toNote()
                }
            }
    }

    override fun getNote(id: Int): Flow<Note> {
        return notesDao
            .getNote(id)
            .map { noteEntity ->
                noteEntity.toNote()
            }
    }

    override suspend fun saveNote(note: Note) {
        notesDao.upsertNote(note.toNoteEntity())
    }

    override suspend fun saveNotes(notes: List<Note>) {
        val notesEntity = notes.map { note ->
            note.toNoteEntity()
        }
        notesDao.upsertNotes(notesEntity)
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note.toNoteEntity())
    }
}