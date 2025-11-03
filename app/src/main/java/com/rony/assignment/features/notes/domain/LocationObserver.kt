package com.rony.assignment.features.notes.domain

import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval: Long): Flow<Location>
}