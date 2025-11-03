package com.rony.assignment.features.notes.presentation.main_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rony.assignment.features.notes.domain.NoteRepository
import com.rony.assignment.features.notes.presentation.mappers.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NotesViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(NotesState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeNotes()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = NotesState()
        )

    fun onAction(action: NotesAction) {
        when (action) {
            is NotesAction.OnNoteTabSelected -> onTabSelected(newTab = action.selectedTab)
            else -> {}
        }
    }

    private fun observeNotes() {
        noteRepository.getAllNotes()
            .onEach { localNotes ->
                val uiNotes = localNotes.map {
                    it.toUi()
                }
                _state.update { it.copy(
                    notes = uiNotes
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun onTabSelected(newTab: ScreenMode) {
        _state.update { it.copy(currentScreenMode = newTab) }
    }
}