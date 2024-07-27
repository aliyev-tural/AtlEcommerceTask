package com.example.ecommercetask.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercetask.data.AuthOperations
import com.example.ecommercetask.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authOperations: AuthOperations
): ViewModel() {

    private val _uiState = MutableStateFlow<SignUpUIState>(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    fun registerWithEmail(username:String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = SignUpUIState(isLoading = true)
            val result = authOperations.registerWithEmail(username,email, password)
            _uiState.value = SignUpUIState(isLoading = false)
            _uiState.value = when (result) {
                is Resource.Success -> SignUpUIState(user = result.data)
                is Resource.Error -> SignUpUIState(error = result.message)
            }
        }
    }



}