package com.example.ecommercetask.presentation.sign_in

import com.google.firebase.auth.FirebaseUser

data class SignInUIState(
    val isLoading:Boolean = false,
    val user: FirebaseUser? = null,
    val error:String ="",
)