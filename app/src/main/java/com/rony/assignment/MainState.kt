package com.rony.assignment

data class MainState(
    val isCheckingSignedUser: Boolean = true,
    val onAuthenticationChecked: Boolean = false
)