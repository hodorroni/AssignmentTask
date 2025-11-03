package com.rony.assignment.features.auth.presentation.register.di

import com.rony.assignment.features.auth.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val registerPresentationModule = module {
    viewModelOf(::RegisterViewModel)
}