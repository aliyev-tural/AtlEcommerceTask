package com.example.ecommercetask.data
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthOperations @Inject constructor(
    private val auth: FirebaseAuth,

    ) {
    suspend fun registerWithEmail(
        username: String,
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        return performOperation(Dispatchers.IO) {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user
            user?.sendEmailVerification()?.await() // Send email verification
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build()
            user?.updateProfile(profileUpdates)?.await()
            user
        }
    }



    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Resource<FirebaseUser?> {
        return performOperation(Dispatchers.IO) {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = result.user
//            if (user != null && !user.isEmailVerified) {
//                user.sendEmailVerification().await() // Optionally, resend verification email
//                throw FirebaseAuthException("ERROR_EMAIL_NOT_VERIFIED", "Email not verified. Please check your email.")
//            }
            user
        }
    }



    fun signOut() {
        auth.signOut()
    }
}