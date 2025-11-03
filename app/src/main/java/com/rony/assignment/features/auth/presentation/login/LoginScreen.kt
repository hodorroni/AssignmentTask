package com.rony.assignment.features.auth.presentation.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme
import com.rony.assignment.core.presentation.design_system.components.buttons.NotesButton
import com.rony.assignment.core.presentation.design_system.components.buttons.NotesButtonStyles
import com.rony.assignment.core.presentation.design_system.components.layouts.NotesSurface
import com.rony.assignment.core.presentation.design_system.components.text_fields.NotesPasswordTextField
import com.rony.assignment.core.presentation.design_system.components.text_fields.NotesTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSucceeded: () -> Unit,
    navigateToRegisterScreen: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginScreen(
        state = state,
        onAction = { action ->
            when(action) {
                LoginAction.OnRegisterClicked -> navigateToRegisterScreen()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )

}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    NotesSurface(
        header = {
            Spacer(modifier = Modifier.height(54.dp))
            Text(
                text = state.title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
        },
        modifier = Modifier
            .fillMaxSize()

    ) {
        Spacer(modifier = Modifier.height(16.dp))

        NotesTextField(
            state = state.emailTextFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = if(state.emailTextFieldState.text.toString().isEmpty()) {
                "Enter your email"
            } else null,
            title = "Email",
            errorText = state.emailError,
            isError = state.emailError != null
        )
        Spacer(modifier = Modifier.height(16.dp))

        NotesPasswordTextField(
            state = state.passwordTextFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = if(state.passwordTextFieldState.text.toString().isEmpty()) {
                "Password"
            } else null,
            title = "Password",
            errorText = state.passwordError,
            isError = state.passwordError != null,
            isPasswordVisible = state.isPasswordVisible,
            enabled = true,
            onToggleVisibilityClick = {
                onAction(LoginAction.OnPasswordVisibilityToggled)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        NotesButton(
            text = "Log In",
            onClick = {
                onAction(LoginAction.OnLoginClicked)
            },
            style = NotesButtonStyles.PRIMARY,
            modifier = Modifier
                .fillMaxWidth(),
            enabled = state.canLogin,
            isLoading = state.isLoggingIn
        )
        Spacer(modifier = Modifier.height(16.dp))

        NotesButton(
            text = "Sign Up",
            onClick = {
                onAction(LoginAction.OnRegisterClicked)
            },
            style = NotesButtonStyles.SECONDARY,
            modifier = Modifier
                .fillMaxWidth(),
            enabled = true,
            isLoading = false
        )
    }
}

@Composable
@Preview
fun LoginScreenPreview() {
    NotesApplicationTheme {
        LoginScreen(
            state = LoginState(
                emailTextFieldState = TextFieldState(),
                passwordTextFieldState = TextFieldState(),
                canLogin = true,
                isLoggingIn = false,
                emailError = null,
                passwordError = null
            ),
            onAction = {}
        )
    }
}