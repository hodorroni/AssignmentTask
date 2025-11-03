package com.rony.assignment.core.domain

fun String.isNameValid(): Boolean {
    return this.isNotBlank() && !this.any { it.isDigit() }
}