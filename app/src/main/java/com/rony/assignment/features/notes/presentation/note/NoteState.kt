package com.rony.assignment.features.notes.presentation.note

import androidx.compose.foundation.text.input.TextFieldState
import com.rony.assignment.features.notes.domain.NoteUi

data class NoteState(
    val isFromCreate: Boolean = false,
    val isEditButtonShown: Boolean = true,
    val titleTextFieldState: TextFieldState = TextFieldState(),
    val titleError: String? = null,
    val descriptionTextFieldState: TextFieldState = TextFieldState(),
    val descError: String? = null,
    val note: NoteUi? = null,
    val imageUri: String? = null,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val canSave: Boolean = false
)