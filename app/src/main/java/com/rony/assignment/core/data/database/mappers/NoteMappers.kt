package com.rony.assignment.core.data.database.mappers

import com.rony.assignment.core.data.database.entity.NoteEntity
import com.rony.assignment.core.domain.notes.Note
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        createdAt = Instant.fromEpochMilliseconds(createdAt),
        imageUri = imageUri
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        description = description,
        createdAt = createdAt?.toEpochMilliseconds() ?: Clock.System.now().toEpochMilliseconds(),
        longitude = longitude,
        latitude = latitude,
        imageUri = imageUri
    )
}