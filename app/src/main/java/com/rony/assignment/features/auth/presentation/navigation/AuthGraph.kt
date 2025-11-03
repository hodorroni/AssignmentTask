package com.rony.assignment.features.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.rony.assignment.features.auth.presentation.login.LoginScreenRoot
import com.rony.assignment.features.auth.presentation.register.RegisterRoot

fun NavGraphBuilder.authGraph(
    navController: NavController,
    onLoginSuccess: () -> Unit,
    onSuccessfullyRegistered: () -> Unit
) {
    navigation<AuthRoutes.Graph>(
        startDestination = AuthRoutes.Login
    ) {
        composable<AuthRoutes.Login> {
            LoginScreenRoot(
                onLoginSucceeded = onLoginSuccess,
                navigateToRegisterScreen = {
                    navController.navigate(AuthRoutes.Register) {
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<AuthRoutes.Register> {
            RegisterRoot(
                onLoginClicked = {
                    navController.navigate(AuthRoutes.Login) {
                        popUpTo(AuthRoutes.Register) {
                            inclusive = true
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onSuccessfullyRegistered = onSuccessfullyRegistered
            )
        }
    }
}