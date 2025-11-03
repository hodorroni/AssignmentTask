package com.rony.assignment.features.auth.data.mappers

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthException
import com.rony.assignment.core.domain.util.DataError

fun mapFirebaseError(e: FirebaseException): DataError.Remote {
    val code = (e as? FirebaseAuthException)?.errorCode

    return when (code) {
        "ERROR_INVALID_EMAIL" -> DataError.Remote.INVALID_EMAIL
        "ERROR_INVALID_CREDENTIAL" -> DataError.Remote.INVALID_CREDENTIALS
        "ERROR_WRONG_PASSWORD" -> DataError.Remote.WRONG_PASSWORD
        "ERROR_USER_NOT_FOUND" -> DataError.Remote.ERROR_USER_NOT_FOUND
        "ERROR_EMAIL_ALREADY_IN_USE" -> DataError.Remote.ERROR_EMAIL_ALREADY_IN_USE
        "ERROR_NETWORK_REQUEST_FAILED" -> DataError.Remote.NETWORK_ERROR
        else -> DataError.Remote.UNKNOWN
    }
}