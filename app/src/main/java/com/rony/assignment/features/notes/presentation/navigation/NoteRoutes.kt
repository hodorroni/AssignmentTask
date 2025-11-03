package com.rony.assignment.features.notes.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NoteRoutes {
    @Serializable
    data object Graph: NoteRoutes

    @Serializable
    data object MainNotes: NoteRoutes
}