package com.rony.assignment.features.auth.presentation.register

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rony.assignment.R
import com.rony.assignment.core.domain.isNameValid
import com.rony.assignment.core.domain.prefs.PreferencesActions
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

class RegisterViewModel(
    private val authService: AuthService,
    private val sharedPrefs: PreferencesActions
) : ViewModel() {

    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(RegisterState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeInputFields()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = RegisterState()
        )

    private val isEmailValidFlow = snapshotFlow { state.value.emailFieldState.text.toString() }
        .map { email ->
            clearInputErrors()
            EmailValidator.validate(email)
        }.distinctUntilChanged()

    private val isPasswordValidFlow = snapshotFlow { state.value.passwordFieldState.text.toString() }
        .map { password ->
            clearInputErrors()
            PasswordValidator.validate(password)
                .isValidPassword
        }.distinctUntilChanged()

    private val isFirstNameValidFlow = snapshotFlow { state.value.firstNameFieldState.text.toString() }
        .map { firstname ->
            clearInputErrors()
            firstname.isNameValid()
        }.distinctUntilChanged()

    private val isLastNameValidFlow = snapshotFlow { state.value.lastNameFieldState.text.toString() }
        .map { lastname ->
            clearInputErrors()
            lastname.isNameValid()
        }.distinctUntilChanged()

    private fun observeInputFields() {
        combine(
            isEmailValidFlow,
            isPasswordValidFlow,
            isFirstNameValidFlow,
            isLastNameValidFlow
        ) { isEmailValid, isPasswordValid, isFirstnameValid, isLastnameValid ->
            val allValid = isEmailValid && isPasswordValid && isFirstnameValid && isLastnameValid
            _state.update { it.copy(
                canRegister = allValid
            ) }
        }.launchIn(viewModelScope)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            RegisterAction.OnPasswordToggle -> onTogglePassword()
            RegisterAction.OnRegisterClicked -> onRegisterClicked()
            else -> Unit
        }
    }

    private fun onRegisterClicked() {
        if(!validateInputFields()) {
            return
        }
        viewModelScope.launch {
            _state.update { it.copy(
                isRegistering = true
            ) }
            sharedPrefs.saveFirstname(
                firstName = state.value.firstNameFieldState.text.toString()
            )
            authService.register(
                email = state.value.emailFieldState.text.toString(),
                password = state.value.passwordFieldState.text.toString(),
            )
                .onSuccess {
                    eventChannel.send(RegisterEvent.OnSuccessfullyRegistered)
                }
                .onFailure { error ->
                    _state.update { it.copy(
                        isRegistering = false,
                        generalError = error.toText()
                    ) }
                }
        }
    }

    private fun validateInputFields(): Boolean {
        clearInputErrors()

        val currentState = state.value
        val email = currentState.emailFieldState.text.toString()
        val password = currentState.passwordFieldState.text.toString()
        val firstname = currentState.firstNameFieldState.text.toString()
        val lastname = currentState.lastNameFieldState.text.toString()

        val isEmailValid = EmailValidator.validate(email)
        val isPasswordValidationState = PasswordValidator.validate(password)
        val isFirstnameValid = firstname.isNameValid()
        val isLastnameValid = lastname.isNameValid()


        val emailError = if(!isEmailValid) {
            R.string.error_email
        } else null

        val passwordError = if(!isPasswordValidationState.isValidPassword) {
            R.string.error_password
        } else null

        val firstnameError = if(!isFirstnameValid) {
            R.string.error_password
        } else null

        val lastnameError = if(!isLastnameValid) {
            R.string.error_lastname
        } else null

        _state.update { it.copy(
            emailError = emailError,
            passwordError = passwordError,
            firstNameError = firstnameError,
            lastNameError = lastnameError
        ) }
        return isEmailValid && isPasswordValidationState.isValidPassword &&
                isFirstnameValid && isLastnameValid
    }

    private fun clearInputErrors() {
        _state.update { it.copy(
            emailError = null,
            passwordError = null,
            firstNameError = null,
            lastNameError = null,
            generalError = null
        ) }
    }

    private fun onTogglePassword() {
        _state.update { it.copy(
            isPasswordVisible = !it.isPasswordVisible
        ) }
    }
}