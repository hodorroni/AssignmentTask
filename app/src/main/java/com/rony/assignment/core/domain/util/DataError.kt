package com.rony.assignment.core.domain.util

sealed interface DataError: Error {
    enum class Remote: DataError {
        INVALID_EMAIL,
        INVALID_CREDENTIALS,
        WRONG_PASSWORD,
        ERROR_USER_NOT_FOUND,
        ERROR_EMAIL_ALREADY_IN_USE,
        NETWORK_ERROR,
        UNKNOWN;
    }

    enum class Local: DataError {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }
}