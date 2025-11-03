package com.rony.assignment.features.notes.presentation.note

sealed interface NoteAction {
    data object OnSaveNote: NoteAction
    data object OnDismissedLocationDialog: NoteAction
    data object OnEditToggled: NoteAction
    data object OnCancelClicked: NoteAction
    data class LocationPermissionInfo(val isAcceptedLocation: Boolean, val showLocationRationale: Boolean): NoteAction
}