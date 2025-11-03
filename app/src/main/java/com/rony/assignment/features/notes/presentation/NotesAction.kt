package com.rony.assignment.features.notes.presentation

sealed interface NotesAction {
    data class OnNoteTabSelected (val selectedTab: ScreenMode): NotesAction
}