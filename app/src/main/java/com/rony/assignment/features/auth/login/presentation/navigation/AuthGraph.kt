package com.rony.assignment.features.auth.login.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rony.assignment.features.auth.login.presentation.LoginScreenRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
) {
    navigation<AuthRoutes.Graph>(
        startDestination = AuthRoutes.Login
    ) {
        composable<AuthRoutes.Login> {
            LoginScreenRoot(
                onLoginSucceeded = {},
                navigateToRegisterScreen = {}
            )
        }
    }
}