package com.rony.assignment.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.rony.assignment.features.auth.login.presentation.navigation.AuthRoutes
import com.rony.assignment.features.auth.login.presentation.navigation.authGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean,
    startDestination: Any = AuthRoutes.Graph
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {
                //TODO move to register screen
            }
        )
    }
}