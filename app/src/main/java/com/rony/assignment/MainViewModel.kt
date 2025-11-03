package com.rony.assignment

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.rony.assignment.MainState
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    private val _state = MutableStateFlow(MainState())



}