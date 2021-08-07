package com.github.alfabravo2013.readyforexams.networking

import com.github.alfabravo2013.readyforexams.util.Result
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebasePasswordAuthService : PasswordAuthService {
    private val auth = Firebase.auth
    private var result: Result? = null

    override suspend fun login(email: String, password: String): Result {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            result = getResult(task)
        }
        return result ?: Result.Failure("No result available")
    }

    override suspend fun logout() = auth.signOut()

    override suspend fun signup(email: String, password: String): Result {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            result = getResult(task)
        }
        return result ?: Result.Failure("No result available")
    }

    override suspend fun resetPassword(email: String): Result {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            result = getResult(task)
        }
        return result ?: Result.Failure("No result available")
    }

    override suspend fun isLoggedIn(): Boolean = auth.currentUser != null

    private fun getResult(task: Task<*>): Result {
        return if (task.isSuccessful) {
            Result.Success
        } else {
            val reason = task.exception?.message ?: "Unknown reason"
            Result.Failure(reason)
        }
    }
}
