package com.rony.assignment.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val createdAt: Long,
    val longitude: Double?,
    val latitude: Double?,
    val imageUri: String? = null
)
