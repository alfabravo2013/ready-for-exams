package com.github.alfabravo2013.readyforexams.networking

import com.github.alfabravo2013.readyforexams.util.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FirebasePasswordAuthService : PasswordAuthService {
    private val auth = Firebase.auth

    override suspend fun login(email: String, password: String): Result =
        withContext(Dispatchers.IO) {
            val loginResult = auth.signInWithEmailAndPassword(email, password)
            return@withContext getResult(loginResult)
        }

    override suspend fun logout() = withContext(Dispatchers.IO) {
        auth.signOut()
    }

    override suspend fun signup(email: String, password: String): Result =
        withContext(Dispatchers.IO) {
            val signupResult = auth.createUserWithEmailAndPassword(email, password)
            return@withContext getResult(signupResult)
        }

    override suspend fun resetPassword(email: String): Result = withContext(Dispatchers.IO) {
        val passwordResetResult = auth.sendPasswordResetEmail(email)
        return@withContext getResult(passwordResetResult)
    }

    override fun isLoggedIn(): Boolean = auth.currentUser != null

    private fun getResult(task: Task<*>): Result {
        return if (task.isSuccessful) {
            Result.Success
        } else {
            val reason = task.exception?.message ?: "Unknown reason"
            Result.Failure(reason)
        }
    }
}
