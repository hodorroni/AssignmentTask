package com.rony.assignment.features.notes.presentation.di

import com.rony.assignment.features.notes.presentation.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val allNotesModule = module {
    viewModelOf(::NotesViewModel)
}