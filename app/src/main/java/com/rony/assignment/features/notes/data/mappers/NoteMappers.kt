package com.rony.assignment.features.notes.data.mappers

import com.rony.assignment.core.domain.notes.Note
import com.rony.assignment.features.notes.domain.NoteUi

fun Note.toUi(): NoteUi {
    return NoteUi(
        id = id,
        title = title,
        description = description,
        latitude = latitude,
        longitude = longitude,
        createdAt = createdAt
    )
}

fun NoteUi.toDomain(): Note {
    return Note(
        id = id,
        title = title ?: "",
        description = description ?: "",
        latitude = latitude,
        longitude = longitude,
        createdAt = createdAt
    )
}