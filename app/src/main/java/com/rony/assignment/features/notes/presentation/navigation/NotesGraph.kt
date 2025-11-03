package com.rony.assignment.features.notes.presentation.navigation

import android.graphics.Paint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

fun NavGraphBuilder.notesGraph(
    navController: NavController
) {
    navigation<NoteRoutes.Graph>(
        startDestination = NoteRoutes.MainNotes
    ) {
        composable<NoteRoutes.MainNotes> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Welcome to notes screen!"
                )
            }
        }
    }
}