package com.github.alfabravo2013.readyforexams.networking

import com.github.alfabravo2013.readyforexams.util.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class FirebasePasswordAuthService : PasswordAuthService {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun login(email: String, password: String): Result =
        withContext(Dispatchers.IO) {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                return@withContext Result.Success
            } catch (e: Exception) {
                return@withContext Result.Failure(e.message ?: "Unknown reason")
            }
        }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        auth.signOut()
    }

    override suspend fun signup(email: String, password: String): Result =
        withContext(Dispatchers.IO) {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                return@withContext Result.Success
            } catch (e: Exception) {
                return@withContext Result.Failure(e.message ?: "Unknown reason")
            }
    }

    override suspend fun resetPassword(email: String): Result = withContext(Dispatchers.IO) {
        try {
            auth.sendPasswordResetEmail(email).await()
            return@withContext Result.Success
        } catch (e: Exception) {
            return@withContext Result.Failure(e.message ?: "Unknown reason")
        }
    }

    override suspend fun isLoggedIn(): Boolean = auth.currentUser != null
}
