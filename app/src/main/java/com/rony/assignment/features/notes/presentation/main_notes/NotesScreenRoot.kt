package com.rony.assignment.features.notes.presentation.main_notes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rony.assignment.core.presentation.design_system.components.NotesFloatingActionButton
import com.rony.assignment.features.notes.presentation.note_modes.ListModeNote
import com.rony.assignment.features.notes.presentation.note_modes.MapMode

@Composable
fun NotesScreenRoot(
    viewModel: NotesViewModel,
    openCreateNoteScreen: () -> Unit,
    openViewNote: (Int) -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    NotesScreen(
        state = state,
        action = { action ->
            when(action) {
                NotesAction.OnFabCreateNewNoteClicked -> openCreateNoteScreen()
                is NotesAction.OnNoteClicked -> openViewNote(action.id)
                else -> Unit
            }
            viewModel.onAction(action = action)
        }
    )
}

@Composable
fun NotesScreen(
    action: (NotesAction) -> Unit,
    state: NotesState
) {
    Scaffold(
        floatingActionButton = {
            NotesFloatingActionButton(
                onClick = {
                    action(NotesAction.OnFabCreateNewNoteClicked)
                },
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .graphicsLayer(
                        translationY = (-300).toFloat()
                    )
            )
        }
    ) {
        NotesScreenWrapper(
            state = state,
            onAction = action,
            content = {
                when(state.currentScreenMode) {
                    ScreenMode.LIST_MODE -> ListModeNote(
                        items = state.notes,
                        onNoteClicked = {
                            action(NotesAction.OnNoteClicked(id = it))
                        },
                        onDeleteNote = {
                            action(NotesAction.OnNoteDeleted(note = it))
                        }
                    )
                    ScreenMode.MAP_MODE -> {
                        MapMode(
                            items = state.mapItemsWithCoordinates,
                            onNoteClicked = {
                                action(NotesAction.OnNoteClicked(id = it))
                            }
                        )
                    }
                }
            }
        )
    }
}