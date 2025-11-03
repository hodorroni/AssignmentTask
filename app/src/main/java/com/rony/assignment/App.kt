package com.rony.assignment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme
import com.rony.assignment.features.auth.presentation.navigation.AuthRoutes
import com.rony.assignment.features.notes.presentation.navigation.NoteRoutes
import com.rony.assignment.navigation.NavigationRoot
import org.koin.androidx.compose.koinViewModel

@Composable
fun App(
    viewModel: MainViewModel = koinViewModel(),
    onAuthenticationChecked: () -> Unit
) {

    val navController = rememberNavController()

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCheckingSignedUser) {
        if(!state.isCheckingSignedUser) {
            onAuthenticationChecked()
        }
    }

    NotesApplicationTheme {
       if(!state.isCheckingSignedUser) {
           val startDestination = if(state.isUserAuthenticated) {
               NoteRoutes.Graph
           } else {
               AuthRoutes.Graph
           }
           NavigationRoot(
               navController = navController,
               startDestination = startDestination
           )
       }
    }
}