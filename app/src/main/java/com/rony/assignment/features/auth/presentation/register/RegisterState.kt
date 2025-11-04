package com.rony.assignment.features.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState

data class RegisterState(
    val emailFieldState: TextFieldState = TextFieldState(),
    val emailError: Int? = null,
    val passwordFieldState: TextFieldState = TextFieldState(),
    val passwordError: Int? = null,
    val isPasswordVisible: Boolean = false,
    val firstNameFieldState: TextFieldState = TextFieldState(),
    val firstNameError: Int? = null,
    val lastNameFieldState: TextFieldState = TextFieldState(),
    val lastNameError: Int? = null,
    val generalError: Int? = null,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)