package com.rony.assignment.features.notes.presentation.note

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rony.assignment.core.domain.notes.Note
import com.rony.assignment.features.auth.domain.validation.EmailValidator
import com.rony.assignment.features.notes.domain.NoteRepository
import com.rony.assignment.features.notes.presentation.mappers.toUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class NoteViewModel(
    private val noteRepository: NoteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val isFromCreateNote: Boolean = savedStateHandle.get<Boolean>("isFromCreateNote") ?: false
    private val noteId = savedStateHandle.get<Int?>("noteId")
    private var hasLoadedInitialData = false

    private var _state = MutableStateFlow(NoteState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                _state.update {
                    it.copy(
                        isFromCreate = isFromCreateNote
                    )
                }
                fetchLocalNoteIfNeeded()
                observeInputFieldValidations()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = NoteState()
        )

    private val isTitleValidFlow = snapshotFlow { state.value.titleTextFieldState.text.toString() }
        .map { title ->
            title.isNotBlank()
        }
        .distinctUntilChanged()

    private val isDescriptionValidFlow = snapshotFlow { state.value.descriptionTextFieldState.text.toString() }
        .map { description ->
            description.isNotBlank()
        }
        .distinctUntilChanged()

    fun onAction(action: NoteAction) {
        when (action) {
            NoteAction.OnEditToggled -> onEditToggled()
            NoteAction.OnSaveNote -> saveNote()
            NoteAction.OnDismissedLocationDialog -> closeLocationDialog()
            is NoteAction.LocationPermissionInfo -> {
                _state.update {
                    it.copy(
                        shouldShowLocationRationale = action.showLocationRationale
                    )
                }
            }
            else -> Unit
        }
    }

    private fun closeLocationDialog() {
        _state.update {
            it.copy(
                shouldShowLocationRationale = false
            )
        }
    }

    private fun observeInputFieldValidations() {
        combine(
            isTitleValidFlow,
            isDescriptionValidFlow
        ){ isTitleValid, isDescriptionValid ->
            _state.update { it.copy(
                canSave = isTitleValid && isDescriptionValid
            ) }
        }.launchIn(viewModelScope)
    }

    private fun saveNote() {
        viewModelScope.launch {
            _state.update { it.copy(
                isSaving = true
            ) }
            val finalUiNote = getFinalUiNoteToSave()
            noteRepository.saveNote(finalUiNote)
            _state.update { it.copy(
                isSaving = false
            ) }
        }
    }

    //isFromCreate = true meaning we will create a new note. Scratch new one!
    //if isFromCreate = false then we are creating our own note from the first time.
    private fun getFinalUiNoteToSave(): Note {
        val currentState = state.value
        return Note(
            id = 0,
            title = currentState.titleTextFieldState.text.toString(),
            description = currentState.descriptionTextFieldState.text.toString(),
            createdAt = Clock.System.now(),
            imageUri = currentState.imageUri
        )
    }

    private fun onEditToggled() {
        _state.update { it.copy(
            isEditButtonShown = !it.isEditButtonShown
        ) }
    }

    private fun fetchLocalNoteIfNeeded() {
        if(!isFromCreateNote) {
            _state.update { it.copy(
                isLoading = true
            ) }
            noteId?.let {
                noteRepository.getNote(noteId).onEach {
                    val noteUi = it.toUi()
                    _state.update { it.copy(
                        note = noteUi,
                        isLoading = false
                    ) }
                }
            }
        }
    }
}