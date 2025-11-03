package com.rony.assignment.features.notes.presentation.navigation

import com.rony.assignment.features.notes.domain.NoteUi
import kotlinx.serialization.Serializable

sealed interface NoteRoutes {
    @Serializable
    data object Graph: NoteRoutes

    @Serializable
    data object MainNotesContainer: NoteRoutes

    @Serializable
    data class NoteScreen(val noteId: Int? = null, val isFromCreateNote: Boolean) : NoteRoutes
}