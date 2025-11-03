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
import com.rony.assignment.features.notes.domain.NoteUi
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
//            val parentEntry = remember(navController) {
//                navController.getBackStackEntry(NoteRoutes.Graph::class)
//            }
//
//            val viewmodel = koinViewModel<NotesViewModel>(
//                viewModelStoreOwner = parentEntry
//            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                val viewModel = koinViewModel<NotesViewModel>()
                NotesScreenRoot(
                    viewModel = viewModel,
                    openCreateNoteScreen = {
                        navController.navigate(NoteRoutes.NoteScreen(isFromCreateNote = true)) {
                            restoreState = true
                        }
                    }
                )
            }
        }

        composable<NoteRoutes.NoteScreen>(
        ) { backStackEntry ->
            //arguments will be handled inside viewmodel savedStateHandle!
            NoteRoot()
        }
    }
}