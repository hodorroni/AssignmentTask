package com.rony.assignment.features.notes.presentation.main_notes

sealed interface NotesAction {
    data class OnNoteTabSelected (val selectedTab: ScreenMode): NotesAction
    data object OnFabCreateNewNoteClicked: NotesAction
    data class OnNoteClicked(val id: Int): NotesAction //will get us to note screen in review mode set!
    data class OnNoteLongClicked(val id: Int): NotesAction //will get us to note screen in edit mode set!
}