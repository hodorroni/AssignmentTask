package com.rony.assignment.features.notes.presentation.main_notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.rony.assignment.features.auth.data.FirebaseAuthService
import com.rony.assignment.features.notes.domain.NoteRepository
import com.rony.assignment.features.notes.domain.NoteUi
import com.rony.assignment.features.notes.presentation.mappers.toDomain
import com.rony.assignment.features.notes.presentation.mappers.toUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val noteRepository: NoteRepository,
    private val firebaseAuth: FirebaseAuth
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

    private val eventChannel = Channel<NotesEvent>()
    val event = eventChannel.receiveAsFlow()

    fun onAction(action: NotesAction) {
        when (action) {
            is NotesAction.OnNoteTabSelected -> onTabSelected(newTab = action.selectedTab)
            is NotesAction.OnNoteDeleted -> onDeleteNote(note = action.note)
            NotesAction.OnLogoutClick -> onLogoutClicked()
            else -> {}
        }
    }

    private fun onLogoutClicked() {
        viewModelScope.launch {
            noteRepository.deleteAllNotes()
            firebaseAuth.signOut()
            eventChannel.send(NotesEvent.OnSuccessfullyLoggedOut)
        }
    }

    private fun onDeleteNote(note: NoteUi) {
        viewModelScope.launch {
            noteRepository.deleteNote(note.toDomain())
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