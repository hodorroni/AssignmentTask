package com.rony.assignment.features.auth.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun RegisterRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    onLoginClicked: () -> Unit,
    onSuccessfullyRegistered: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.events) { event ->
        when(event) {
            RegisterEvent.OnSuccessfullyRegistered -> onSuccessfullyRegistered()
        }
    }

    RegisterScreen(
        state = state,
        onAction = { action ->
            when(action) {
                RegisterAction.OnLoginClicked -> onLoginClicked()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit,
) {
    NotesAdaptiveLayout(
        shouldIncludeVerticalScroll = true,
        header = {
            Spacer(modifier = Modifier.height(54.dp))
            Text(
                text = stringResource(id = R.string.registration_form),
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AnimatedVisibility(state.generalError != null) {
            state.generalError?.let { error ->
                Text(
                    text = stringResource(id = error),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 18.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        NotesTextField(
            state = state.emailFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = stringResource(R.string.placeholder_email),
            title = stringResource(R.string.title_email),
            errorText = state.emailError?.let { stringResource(it) },
            isError = state.emailError != null
        )
        Spacer(modifier = Modifier.height(16.dp))
        NotesPasswordTextField(
            state = state.passwordFieldState,
            isPasswordVisible = state.isPasswordVisible,
            onToggleVisibilityClick = {
                onAction(RegisterAction.OnPasswordToggle)
            },
            modifier = Modifier.fillMaxWidth(),
            placeHolder = stringResource(R.string.placeholder_password),
            title = stringResource(R.string.placeholder_password),
            supportingText = stringResource(R.string.error_password),
            errorText = state.passwordError?.let { stringResource(it) },
            isError = state.passwordError != null
        )
        Spacer(modifier = Modifier.height(16.dp))
        NotesTextField(
            state = state.firstNameFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = stringResource(R.string.placeholder_first_name),
            title = stringResource(R.string.placeholder_first_name),
            errorText = state.firstNameError?.let { stringResource(it) },
            isError = state.firstNameError != null
        )
        Spacer(modifier = Modifier.height(16.dp))
        NotesTextField(
            state = state.lastNameFieldState,
            modifier = Modifier
                .fillMaxWidth(),
            placeHolder = stringResource(R.string.placeholder_last_name),
            title = stringResource(R.string.placeholder_last_name),
            errorText = state.lastNameError?.let { stringResource(it) },
            isError = state.lastNameError != null
        )
        Spacer(modifier = Modifier.height(16.dp))

        NotesButton(
            text = stringResource(R.string.register),
            onClick = {
                onAction(RegisterAction.OnRegisterClicked)
            },
            style = NotesButtonStyles.PRIMARY,
            modifier = Modifier
                .fillMaxWidth(),
            enabled = state.canRegister,
            isLoading = state.isRegistering
        )
        Spacer(modifier = Modifier.height(16.dp))

        NotesButton(
            text = stringResource(R.string.login),
            onClick = {
                onAction(RegisterAction.OnLoginClicked)
            },
            style = NotesButtonStyles.SECONDARY,
            modifier = Modifier
                .fillMaxWidth(),
            enabled = true,
            isLoading = false
        )
    }
}

@Preview
@Composable
private fun RegisterPreview() {
    NotesApplicationTheme(darkTheme = true) {
        RegisterScreen(
            state = RegisterState(),
            onAction = {
                
            }
        )
    }
}