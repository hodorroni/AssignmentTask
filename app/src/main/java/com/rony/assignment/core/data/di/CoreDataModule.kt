package com.rony.assignment.core.data.di

import androidx.room.Room
import com.rony.assignment.core.data.database.NotesDatabase
import com.rony.assignment.features.auth.data.FirebaseAuthService
import com.rony.assignment.features.auth.domain.AuthService
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            NotesDatabase::class.java,
            "notes.db"
        ).build()
    }

    single { get<NotesDatabase>().noteDao }

    singleOf(::FirebaseAuthService) bind AuthService::class
}