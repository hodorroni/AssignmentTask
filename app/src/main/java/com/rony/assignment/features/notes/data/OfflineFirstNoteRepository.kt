package com.rony.assignment.features.notes.data

import com.rony.assignment.core.data.database.mappers.toNoteEntity
import com.rony.assignment.core.domain.notes.LocalNoteDataSource
import com.rony.assignment.core.domain.notes.Note
import com.rony.assignment.features.notes.data.mappers.toUi
import com.rony.assignment.features.notes.domain.NoteRepository
import com.rony.assignment.features.notes.domain.NoteUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineFirstNoteRepository(
    private val localNoteDataSource: LocalNoteDataSource
): NoteRepository {

    override fun getAllNotes(): Flow<List<NoteUi>> {
        return localNoteDataSource
            .getAllNotes()
            .map { notes ->
                notes.map { note ->
                    note.toUi()
                }
            }
    }

    override fun getNote(id: Int): Flow<NoteUi> {
        return localNoteDataSource
            .getNote(id)
            .map {
                it.toUi()
            }
    }

    override suspend  fun deleteNote(note: Note) {
        localNoteDataSource.deleteNote(note)
    }

    override suspend fun saveNotes(notes: List<Note>) {
        localNoteDataSource.saveNotes(notes)
    }

    override suspend fun saveNote(note: Note) {
        localNoteDataSource.saveNote(note)
    }
}