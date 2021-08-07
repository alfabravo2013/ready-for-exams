package com.github.alfabravo2013.readyforexams.domain.login

import com.github.alfabravo2013.readyforexams.util.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginRemoteDataSource {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    private var currentUser: FirebaseUser? = auth.currentUser

    fun login(email: String, password: String): Result {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                currentUser = if (task.isSuccessful) {
                    auth.currentUser
                } else {
                    null
                }
            }

        return if (currentUser == null) {
            Result.Failure("Failed to log in")
        } else {
            Result.Success
        }
    }

    fun isSignedIn(): Boolean = currentUser != null
}
