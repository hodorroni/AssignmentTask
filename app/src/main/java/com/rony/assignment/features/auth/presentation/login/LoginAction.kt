package com.rony.assignment.features.auth.presentation.login

sealed interface LoginAction {
    data object OnLoginClicked : LoginAction
    data object OnPasswordVisibilityToggled: LoginAction
    data object OnRegisterClicked : LoginAction
}