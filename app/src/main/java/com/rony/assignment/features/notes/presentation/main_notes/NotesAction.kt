package com.rony.assignment.features.notes.presentation.main_notes

import com.rony.assignment.features.notes.domain.NoteUi

sealed interface NotesAction {
    data class OnNoteTabSelected (val selectedTab: ScreenMode): NotesAction
    data class OnNoteDeleted (val note: NoteUi): NotesAction
    data object OnFabCreateNewNoteClicked: NotesAction
    data class OnNoteClicked(val id: Int): NotesAction //will get us to note screen in review mode set!
}