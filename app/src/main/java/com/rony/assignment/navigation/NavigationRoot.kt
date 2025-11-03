package com.rony.assignment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rony.assignment.features.auth.presentation.navigation.AuthRoutes
import com.rony.assignment.features.auth.presentation.navigation.authGraph
import com.rony.assignment.features.notes.presentation.navigation.NoteRoutes
import com.rony.assignment.features.notes.presentation.navigation.notesGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any = AuthRoutes.Graph
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {
                navigateToMainScreen(navController = navController)
            },
            onSuccessfullyRegistered = {
                navigateToMainScreen(navController = navController)
            }
        )

        notesGraph(
            navController = navController
        )
    }
}

private fun navigateToMainScreen(navController: NavHostController) {
    navController.navigate(NoteRoutes.Graph) {
        popUpTo(AuthRoutes.Graph) {
            inclusive = true
        }
    }
}