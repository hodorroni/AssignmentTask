package com.rony.assignment.features.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState

data class LoginState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val canLogin: Boolean = false,
    val isLoggingIn: Boolean = false,
    val emailError: Int? = null,
    val generalError: Int? = null,
    val passwordError: Int? = null,
    val isPasswordVisible: Boolean = false
)
