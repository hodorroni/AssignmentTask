package com.rony.assignment.features.auth.presentation.login

sealed interface LoginAction {
    data object OnLoginClicked : LoginAction
}