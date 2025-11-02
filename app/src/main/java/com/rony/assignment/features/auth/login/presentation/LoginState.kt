package com.rony.assignment.features.auth.login.presentation

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isPasswordVisible: Boolean = false
)
