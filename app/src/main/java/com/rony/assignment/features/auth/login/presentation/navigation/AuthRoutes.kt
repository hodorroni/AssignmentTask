package com.rony.assignment.features.auth.login.presentation.navigation

import kotlinx.serialization.Serializable


sealed interface AuthRoutes {
    @Serializable
    data object Graph: AuthRoutes

    @Serializable
    data object Login: AuthRoutes

    @Serializable
    data object Register: AuthRoutes
}