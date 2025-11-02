package com.rony.assignment.features.auth.login.presentation.di

import com.rony.assignment.features.auth.login.presentation.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::LoginViewModel)
}