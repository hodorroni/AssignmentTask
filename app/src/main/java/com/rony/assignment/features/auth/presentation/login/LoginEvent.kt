package com.rony.assignment.features.auth.presentation.login

sealed interface LoginEvent {
    data object OnSuccessfullyLoggedIn: LoginEvent
}