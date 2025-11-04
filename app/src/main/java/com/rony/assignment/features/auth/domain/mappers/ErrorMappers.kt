package com.rony.assignment.features.auth.domain.mappers

import androidx.annotation.StringRes
import com.rony.assignment.R
import com.rony.assignment.core.domain.util.DataError

@StringRes
fun DataError.Remote.toText(): Int {
    return when (this) {
        DataError.Remote.INVALID_EMAIL -> R.string.error_email
        DataError.Remote.INVALID_CREDENTIALS -> R.string.error_invalid_credentials
        DataError.Remote.WRONG_PASSWORD -> R.string.error_wrong_password
        DataError.Remote.ERROR_USER_NOT_FOUND -> R.string.error_user_not_found
        DataError.Remote.ERROR_EMAIL_ALREADY_IN_USE -> R.string.error_email_in_use
        DataError.Remote.NETWORK_ERROR -> R.string.error_network
        DataError.Remote.UNKNOWN -> R.string.error_unknown
    }
}