package com.rony.assignment.features.auth.presentation.login.di

import com.rony.assignment.features.auth.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::LoginViewModel)
}