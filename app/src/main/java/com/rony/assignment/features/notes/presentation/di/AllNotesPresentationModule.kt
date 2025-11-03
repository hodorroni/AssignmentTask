package com.rony.assignment.features.notes.presentation.di

import com.rony.assignment.features.notes.presentation.main_notes.NotesViewModel
import com.rony.assignment.features.notes.presentation.note.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val allNotesModule = module {
    viewModelOf(::NotesViewModel)
    viewModelOf(::NoteViewModel)
}