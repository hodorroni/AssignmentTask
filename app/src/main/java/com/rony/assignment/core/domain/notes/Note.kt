package com.rony.assignment.core.domain.notes

import kotlinx.datetime.Instant

data class Note(
    val id: Int = 0,
    val title: String,
    val description: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val createdAt: Instant? = null,
    val imageUri: String? = null
)