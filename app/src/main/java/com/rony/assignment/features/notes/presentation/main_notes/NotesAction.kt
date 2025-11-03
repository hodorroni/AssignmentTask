package com.rony.assignment.features.notes.presentation.main_notes

sealed interface NotesAction {
    data class OnNoteTabSelected (val selectedTab: ScreenMode): NotesAction
    data object OnFabCreateNewNoteClicked: NotesAction
}