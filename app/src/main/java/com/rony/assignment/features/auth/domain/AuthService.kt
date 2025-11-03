package com.rony.assignment.features.auth.domain

import com.rony.assignment.core.domain.util.DataError
import com.rony.assignment.core.domain.util.Result

interface AuthService {
    suspend fun register(email: String, password: String): Result<Unit, DataError.Remote>
    suspend fun login(email: String, password: String): Result<Unit, DataError.Remote>
}