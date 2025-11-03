package com.rony.assignment.features.auth.data

import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.rony.assignment.core.domain.util.DataError
import com.rony.assignment.core.domain.util.EmptyResult
import com.rony.assignment.core.domain.util.Result
import com.rony.assignment.core.domain.util.emptySuccess
import com.rony.assignment.features.auth.data.mappers.mapFirebaseError
import com.rony.assignment.features.auth.domain.AuthService
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class FirebaseAuthService(
    private val firebaseAuth: FirebaseAuth
): AuthService {

    override suspend fun register(email: String, password: String): Result<Unit, DataError.Remote> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email,password).await()
            emptySuccess()
        } catch (e: FirebaseException) {
            Result.Failure(mapFirebaseError(e))
        }
        catch (e: CancellationException) {
            Result.Failure(DataError.Remote.UNKNOWN)
        }
        catch (e: Exception) {
            Result.Failure(DataError.Remote.UNKNOWN)
        }
    }

    override suspend fun login(email: String, password: String): Result<Unit, DataError.Remote> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emptySuccess()
        } catch (e: FirebaseException) {
            Result.Failure(mapFirebaseError(e))
        }
        catch (e: CancellationException) {
            Result.Failure(DataError.Remote.UNKNOWN)
        }
        catch (e: Exception) {
            Result.Failure(DataError.Remote.UNKNOWN)
        }
    }
}