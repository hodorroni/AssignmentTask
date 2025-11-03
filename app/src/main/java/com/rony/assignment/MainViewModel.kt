package com.rony.assignment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            if(!hasLoadedInitialData) {
                observeAuthentication()
                hasLoadedInitialData = true
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            MainState()
        )

    private fun observeAuthentication() {
        _state.update { it.copy(
            isUserAuthenticated = firebaseAuth.currentUser != null,
            isCheckingSignedUser = false
        ) }
    }
}