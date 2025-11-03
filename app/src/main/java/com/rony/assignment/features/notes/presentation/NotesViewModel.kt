package com.rony.assignment.features.notes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NotesViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(NotesState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
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
        }
    }

    private fun onTabSelected(newTab: ScreenMode) {
        _state.update { it.copy(currentScreenMode = newTab) }
    }
}