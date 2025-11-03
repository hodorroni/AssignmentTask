package com.rony.assignment.core.data.di

import com.rony.assignment.features.auth.data.FirebaseAuthService
import com.rony.assignment.features.auth.domain.AuthService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    singleOf(::FirebaseAuthService) bind AuthService::class
}