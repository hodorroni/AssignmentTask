package com.rony.assignment.features.auth.presentation.register

sealed interface RegisterAction {
    data object OnLoginClicked: RegisterAction
    data object OnRegisterClicked: RegisterAction
    data object OnPasswordToggle: RegisterAction
}