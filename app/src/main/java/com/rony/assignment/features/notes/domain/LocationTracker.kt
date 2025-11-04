@file:OptIn(ExperimentalCoroutinesApi::class)

package com.rony.assignment.features.notes.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber

class LocationTracker(
    private val locationObserver: LocationObserver,
    private val viewmodelScope: CoroutineScope,
) {

    private val isObservingLocation = MutableStateFlow(false)

    val currentLocation = isObservingLocation
        .flatMapLatest { isObservingLocation ->
            if(isObservingLocation) {
                locationObserver.observeLocation(interval = 2000)
            } else flowOf()
        }
        .stateIn(
            viewmodelScope,
            SharingStarted.Lazily,
            null
        )

    fun startObservingLocation() {
        isObservingLocation.value = true
    }

    fun stopObservingLocation() {
        isObservingLocation.value = false
    }
}