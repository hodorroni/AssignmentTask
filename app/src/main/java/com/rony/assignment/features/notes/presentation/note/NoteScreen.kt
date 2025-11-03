package com.rony.assignment.features.notes.presentation.note

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rony.assignment.R
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme
import com.rony.assignment.core.presentation.design_system.components.buttons.NotesButton
import com.rony.assignment.core.presentation.design_system.components.buttons.NotesButtonStyles
import com.rony.assignment.core.presentation.design_system.components.layouts.NotesSurface
import com.rony.assignment.core.presentation.design_system.components.text_fields.NotesTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoteRoot(
    viewModel: NoteViewModel = koinViewModel()

) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NoteScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun NoteScreen(
    state: NoteState,
    onAction: (NoteAction) -> Unit,
) {
    val context = LocalContext.current
    NotesSurface(
        header = {
            Spacer(modifier = Modifier.height(64.dp))
        },
        shouldIncludeVerticalScroll = true,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        if (state.note?.imageUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(state.note.imageUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Note image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .widthIn(min = 100.dp, max = 200.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally)
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(R.drawable.anonymous)
                    .crossfade(true)
                    .build(),
                contentDescription = "Default image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .widthIn(min = 100.dp, max = 200.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.CenterHorizontally)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        NotesTextField(
            state = state.titleTextFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = if(state.isFromCreate) {
                "Title"
            } else null,
            title = "Title",
            errorText = state.titleError,
            isError = state.titleError != null,
            singleLine = true,
            enabled = state.isFromCreate
        )

        Spacer(modifier = Modifier.height(16.dp))
        NotesTextField(
            state = state.descriptionTextFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = if(state.isFromCreate) {
                "Description"
            } else null,
            title = "Description",
            errorText = state.descError,
            isError = state.descError != null,
            singleLine = true,
            enabled = state.isFromCreate
        )

        Spacer(modifier = Modifier.height(16.dp))
        if(state.isFromCreate) {
            IsFromCreateNewNoteButtons(
                isSaveButtonLoading = state.isSaving,
                canSave = state.canSave,
                onCancelClicked = {
                    onAction(NoteAction.OnCancelClicked)
                },
                onSaveClicked = {
                    onAction(NoteAction.OnSaveNote)
                },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            DefaultButtons(
                isEditButtonShown = state.isEditButtonShown,
                canSave = state.canSave,
                isSaveButtonLoading = state.isSaving,
                onCancelClicked = {
                    onAction(NoteAction.OnCancelClicked)
                },
                onEditToggled = {
                    onAction(NoteAction.OnEditToggled)
                },
                onSaveClicked = {
                    onAction(NoteAction.OnSaveNote)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DefaultButtons(
    isEditButtonShown: Boolean,
    canSave: Boolean,
    isSaveButtonLoading: Boolean,
    onCancelClicked: () -> Unit,
    onEditToggled: () -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        NotesButton(
            text = "Cancel",
            onClick = onCancelClicked,
            modifier = Modifier.weight(1f),
            style = NotesButtonStyles.SECONDARY
        )

        Spacer(modifier = Modifier.width(16.dp))

        AnimatedVisibility(visible = isEditButtonShown) {
            NotesButton(
                text = "Edit",
                onClick = onEditToggled,
                style = NotesButtonStyles.PRIMARY
            )
        }

        AnimatedVisibility(visible = !isEditButtonShown) {
            NotesButton(
                text = "Save",
                onClick = onSaveClicked,
                style = NotesButtonStyles.PRIMARY,
                enabled = !isSaveButtonLoading && canSave,
                isLoading = isSaveButtonLoading
            )
        }
    }
}

@Composable
fun IsFromCreateNewNoteButtons(
    isSaveButtonLoading: Boolean,
    canSave: Boolean,
    onCancelClicked: () -> Unit,
    onSaveClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        NotesButton(
            text = "Cancel",
            onClick = onCancelClicked,
            modifier = Modifier
                .weight(1f),
            style = NotesButtonStyles.SECONDARY,
            enabled = true
        )
        Spacer(modifier = Modifier.width(10.dp))
        NotesButton(
            text = "Save",
            onClick = onSaveClicked,
            modifier = Modifier
                .weight(1f),
            style = NotesButtonStyles.PRIMARY,
            enabled = !isSaveButtonLoading && canSave,
            isLoading = isSaveButtonLoading,
        )
    }
}

@Preview
@Composable
private fun NotePreview() {
    NotesApplicationTheme {
        NoteScreen(
            state = NoteState(),
            onAction = {}
        )
    }
}