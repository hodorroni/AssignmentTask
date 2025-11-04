package com.rony.assignment.features.auth.presentation.login

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rony.assignment.R
import com.rony.assignment.core.presentation.design_system.NotesApplicationTheme
import com.rony.assignment.core.presentation.design_system.components.buttons.NotesButton
import com.rony.assignment.core.presentation.design_system.components.buttons.NotesButtonStyles
import com.rony.assignment.core.presentation.design_system.components.layouts.NotesAdaptiveLayout
import com.rony.assignment.core.presentation.design_system.components.layouts.NotesSurface
import com.rony.assignment.core.presentation.design_system.components.text_fields.NotesPasswordTextField
import com.rony.assignment.core.presentation.design_system.components.text_fields.NotesTextField
import com.rony.assignment.core.presentation.utils.ObserveAsEvents
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSucceeded: () -> Unit,
    navigateToRegisterScreen: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when(event) {
            LoginEvent.OnSuccessfullyLoggedIn -> onLoginSucceeded()
        }
    }

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
    NotesAdaptiveLayout(
        shouldIncludeVerticalScroll = true,
        header = {
            Spacer(modifier = Modifier.height(54.dp))
            Text(
                text = stringResource(id = R.string.login_title),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
        },
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(state.generalError != null) {
            state.generalError?.let {
                Text(
                    text = stringResource(id = it),
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        NotesTextField(
            state = state.emailTextFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = if(state.emailTextFieldState.text.toString().isEmpty()) {
                stringResource(id = R.string.placeholder_email)
            } else null,
            title = stringResource(id = R.string.title_email),
            errorText = state.emailError?.let { stringResource(id = it) },
            isError = state.emailError != null
        )
        Spacer(modifier = Modifier.height(16.dp))

        NotesPasswordTextField(
            state = state.passwordTextFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = if(state.passwordTextFieldState.text.toString().isEmpty()) {
                stringResource(R.string.placeholder_password)
            } else null,
            title = stringResource(R.string.placeholder_password),
            errorText = state.emailError?.let { stringResource(id = it) },
            isError = state.passwordError != null,
            isPasswordVisible = state.isPasswordVisible,
            supportingText = stringResource(R.string.error_password),
            enabled = true,
            onToggleVisibilityClick = {
                onAction(LoginAction.OnPasswordVisibilityToggled)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        NotesButton(
            text = stringResource(R.string.login),
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
            text = stringResource(R.string.sign_up),
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