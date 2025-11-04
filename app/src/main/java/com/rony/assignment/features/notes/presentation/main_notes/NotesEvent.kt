package com.rony.assignment.features.notes.presentation.main_notes

sealed interface NotesEvent {
    data object OnSuccessfullyLoggedOut: NotesEvent
}