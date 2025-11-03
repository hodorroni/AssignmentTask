package com.rony.assignment.features.notes.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface NoteRoutes {
    @Serializable
    data object Graph: NoteRoutes

    @Serializable
    data object MainNotesContainer: NoteRoutes

    @Serializable
    data object ListNotes: NoteRoutes

    @Serializable
    data object MapNotes: NoteRoutes

    @Serializable
    data object NoteScreen: NoteRoutes

}