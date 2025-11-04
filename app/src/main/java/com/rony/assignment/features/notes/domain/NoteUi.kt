package com.rony.assignment.features.notes.domain

import com.rony.assignment.core.domain.formatToReadableString
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

data class NoteUi(
    val id: Int = 0,
    val title: String? = null,
    val description: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val address: String? = null,
    val createdAt: Instant? = null,
    val imageUri: String? = null
) {
    val formattedDate = createdAt?.formatToReadableString()
}