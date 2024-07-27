package com.example.ecommercetask.presentation.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercetask.data.AuthOperations
import com.example.ecommercetask.data.Resource
import com.example.ecommercetask.presentation.sign_in.SignInUIState
import com.google.firebase.auth.FirebaseAuthException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authOperations: AuthOperations
): ViewModel() {

    private val _uiState = MutableStateFlow<SignInUIState>(SignInUIState())
    val uiState: StateFlow<SignInUIState> = _uiState.asStateFlow()

    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = SignInUIState(isLoading = true)
            val signInResult = authOperations.signInWithEmailAndPassword(email, password)
            val newState = when (signInResult) {
                is Resource.Error -> {
                    SignInUIState(
                        isLoading = false,
                        error = signInResult.message
                    )
                }
                is Resource.Success -> {
                    val user = signInResult.data
                    if (user?.isEmailVerified == true) {
                        SignInUIState(
                            isLoading = false,
                            user = user
                        )
                    } else {
                        SignInUIState(
                            isLoading = false,
                            error = "Email is not verified. Please check your email"
                        )
                    }
                }
                else -> SignInUIState(isLoading = false)
            }
            _uiState.value = newState
        }
    }
}