package com.rony.assignment.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rony.assignment.core.data.database.dao.NotesDao
import com.rony.assignment.core.data.database.entity.NoteEntity

@Database(
    entities = [
        NoteEntity::class
    ],
    version = 1
)
abstract class NotesDatabase: RoomDatabase() {

    abstract val noteDao: NotesDao
}