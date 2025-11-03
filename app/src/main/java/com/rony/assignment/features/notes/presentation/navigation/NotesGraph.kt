package com.rony.assignment.features.notes.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rony.assignment.features.notes.presentation.NotesRoot
import com.rony.assignment.features.notes.presentation.NotesScreen

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
                NotesRoot()
            }
        }
    }
}