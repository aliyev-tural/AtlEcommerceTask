package com.example.ecommercetask.data

import android.util.Log
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String, val exception: Exception? = null) : Resource<Nothing>()
}

suspend fun <T> performOperation(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    operation: suspend () -> T
): Resource<T> {
    return withContext(dispatcher) {
        try {
            val result = operation()
            Resource.Success(result)
        } catch (e: Exception) {
            Log.e("ERROR_STACK_TRACE", e.stackTraceToString()) // Use Log.e for errors
            Resource.Error(mapExceptionToMessage(e), e)
        }
    }
}

private fun mapExceptionToMessage(e: Exception): String {
    return when (e) {
        is IOException, is FirebaseNetworkException -> "Network error. Please check your internet connection."
        is FirebaseAuthException -> mapFirebaseException(e)
        is HttpException -> mapHttpException(e)
        else -> e.message ?: "An error occurred. Please try again later."
    }
}

private fun mapFirebaseException(e: FirebaseAuthException): String {
    return when (e) {
        is FirebaseAuthWeakPasswordException -> e.reason ?: "Password is too weak."
        is FirebaseAuthInvalidCredentialsException -> "Invalid email or password."
        is FirebaseAuthRecentLoginRequiredException -> "You have signed in too long ago. Please sign in again."
        is FirebaseAuthUserCollisionException -> "This email is already in use."
        is FirebaseAuthInvalidUserException -> "Invalid user. Please check your credentials."
        is FirebaseAuthEmailException -> "Invalid email format."
        else -> e.message ?: "An error occurred. Please try again later."
    }
}

private fun mapHttpException(e: HttpException): String {
    return when (val statusCode = e.code()) {
        in 400..499 -> "Client error (e.g., bad request, unauthorized)."
        in 500..599 -> "Server error. Please try again later."
        else -> "API error (code: $statusCode)."
    }
}
