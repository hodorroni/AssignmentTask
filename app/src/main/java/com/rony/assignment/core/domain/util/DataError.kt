package com.rony.assignment.core.domain.util

sealed interface DataError: Error {
    enum class Remote: DataError {
        INVALID_EMAIL,
        WRONG_PASSWORD,
        ERROR_USER_NOT_FOUND,
        ERROR_EMAIL_ALREADY_IN_USE,
        UNKNOWN;
    }

    enum class Local: DataError {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }
}