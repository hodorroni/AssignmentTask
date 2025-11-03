package com.rony.assignment.features.notes.domain.di

import com.rony.assignment.features.notes.data.OfflineFirstNoteRepository
import com.rony.assignment.features.notes.domain.NoteRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val noteDomainModule = module {
    singleOf(::OfflineFirstNoteRepository).bind<NoteRepository>()
}