package com.rony.assignment.features.notes.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.rony.assignment.core.presentation.utils.ObserveAsEvents
import com.rony.assignment.features.auth.presentation.navigation.AuthRoutes
import com.rony.assignment.features.notes.domain.NoteUi
import com.rony.assignment.features.notes.presentation.main_notes.NotesEvent
import com.rony.assignment.features.notes.presentation.main_notes.NotesScreenRoot
import com.rony.assignment.features.notes.presentation.main_notes.NotesViewModel
import com.rony.assignment.features.notes.presentation.note.NoteRoot
import com.rony.assignment.features.notes.presentation.note.NoteScreen
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import kotlin.reflect.typeOf

fun NavGraphBuilder.notesGraph(
    navController: NavController
) {
    navigation<NoteRoutes.Graph>(
        startDestination = NoteRoutes.MainNotesContainer
    ) {
        composable<NoteRoutes.MainNotesContainer> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val viewModel = koinViewModel<NotesViewModel>() //shared viewmodel for list Mode + MapMode screens.

                ObserveAsEvents(viewModel.event) { event ->
                    when(event) {
                        NotesEvent.OnSuccessfullyLoggedOut -> {
                            navController.navigate(AuthRoutes.Graph) {
                                popUpTo(NoteRoutes.Graph) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
                NotesScreenRoot(
                    viewModel = viewModel,
                    openCreateNoteScreen = {
                        navController.navigate(NoteRoutes.NoteScreen(isFromCreateNote = true)) {
                            restoreState = true
                        }
                    },
                    openViewNote = {
                        navController.navigate(NoteRoutes.NoteScreen(isFromCreateNote = false, noteId = it)) {
                            restoreState = true
                        }
                    }
                )
            }
        }

        composable<NoteRoutes.NoteScreen>(
        ) {
            //arguments will be handled inside viewmodel savedStateHandle!
            NoteRoot(
                onCancelClicked = {
                    navController.popBackStack()
                },
                onSuccessSavingNote = {
                    navController.popBackStack()
                }
            )
        }
    }
}