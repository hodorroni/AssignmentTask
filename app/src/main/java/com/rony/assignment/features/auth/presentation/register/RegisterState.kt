package com.rony.assignment.features.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState

data class RegisterState(
    val title: String = "Registration Form",
    val emailFieldState: TextFieldState = TextFieldState(),
    val emailError: String? = null,
    val passwordFieldState: TextFieldState = TextFieldState(),
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false,
    val firstNameFieldState: TextFieldState = TextFieldState(),
    val firstNameError: String? = null,
    val lastNameFieldState: TextFieldState = TextFieldState(),
    val lastNameError: String? = null,
    val generalError: String? = null,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)