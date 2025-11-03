package com.rony.assignment.features.notes.presentation.note

sealed interface NoteEvent {
    data object OnSuccessfullySavedNote: NoteEvent
}