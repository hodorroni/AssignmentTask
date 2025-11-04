package com.rony.assignment.features.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rony.assignment.R
import com.rony.assignment.core.domain.util.onFailure
import com.rony.assignment.core.domain.util.onSuccess
import com.rony.assignment.features.auth.domain.AuthService
import com.rony.assignment.features.auth.domain.mappers.toText
import com.rony.assignment.features.auth.domain.validation.EmailValidator
import com.rony.assignment.features.auth.domain.validation.PasswordValidator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel(
    private val authService: AuthService
): ViewModel() {

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if(!hasLoadedInitialData) {
                observeValidationStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )

    private val isEmailValidFlow = snapshotFlow { state.value.emailTextFieldState.text.toString() }
        .map { email ->
            clearAllInputErrors()
            EmailValidator.validate(email)
        }
        .distinctUntilChanged()

    private val isPasswordValidFlow = snapshotFlow { state.value.passwordTextFieldState.text.toString() }
        .map { email ->
            clearAllInputErrors()
            PasswordValidator.validate(email)
                .isValidPassword
        }
        .distinctUntilChanged()

    fun onAction(action: LoginAction) {
        when(action) {
            LoginAction.OnLoginClicked -> onLoginClicked()
            LoginAction.OnPasswordVisibilityToggled -> onPasswordToggled()
            else -> Unit
        }
    }

    private fun observeValidationStates() {
        combine(
            isEmailValidFlow,
            isPasswordValidFlow
        ) { isEmail, isPassword ->
            _state.update { it.copy(
                canLogin = isEmail && isPassword
            ) }
        }.launchIn(viewModelScope)
    }

    private fun onPasswordToggled() {
        _state.update { it.copy(
            isPasswordVisible = !it.isPasswordVisible
        ) }
    }

    private fun onLoginClicked() {
        if(!validateInputs()) {
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoggingIn = true
                )
            }

            val email = state.value.emailTextFieldState.text.toString()
            val password = state.value.passwordTextFieldState.text.toString()
            authService.login(email = email, password = password)
                .onSuccess {
                    eventChannel.send(LoginEvent.OnSuccessfullyLoggedIn)
                }
                .onFailure { error ->
                    _state.update { it.copy(
                        isLoggingIn = false,
                        generalError = error.toText()
                    ) }
                }
        }
    }

    private fun validateInputs(): Boolean {
        clearAllInputErrors()

        val current = state.value
        val email = current.emailTextFieldState.text.toString()
        val password = current.passwordTextFieldState.text.toString()

        val isEmailValid = EmailValidator.validate(email)
        val isPasswordValidationState = PasswordValidator.validate(password)

        val emailError = if(!isEmailValid) {
            R.string.error_email
        } else null

        val passwordError = if(!isPasswordValidationState.isValidPassword) {
            R.string.error_password
        } else null

        _state.update { it.copy(
            emailError = emailError,
            passwordError = passwordError
        ) }
        return isEmailValid && isPasswordValidationState.isValidPassword
    }

    private fun clearAllInputErrors() {
        _state.update { it.copy(
            emailError = null,
            passwordError = null,
            generalError = null
        ) }
    }
}