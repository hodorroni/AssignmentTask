package com.rony.assignment.features.notes.presentation.note

sealed interface NoteAction {
    data object OnSaveNote: NoteAction
    data object OnEditToggled: NoteAction
    data object OnCancelClicked: NoteAction

}