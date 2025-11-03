package com.rony.assignment.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.rony.assignment.core.data.database.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Upsert
    suspend fun upsertNote(note: NoteEntity)

    @Upsert
    suspend fun upsertNotes(notes: List<NoteEntity>)

    @Query("SELECT * FROM notes ORDER by createdAt DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM NOTES where id=:id")
    fun getNote(id: Int): Flow<NoteEntity>

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}