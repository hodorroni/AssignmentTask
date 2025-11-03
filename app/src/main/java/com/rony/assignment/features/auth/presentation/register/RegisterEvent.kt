package com.rony.assignment.features.auth.presentation.register

sealed interface RegisterEvent {
    data object OnSuccessfullyRegistered: RegisterEvent
}