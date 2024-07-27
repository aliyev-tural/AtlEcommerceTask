package com.example.ecommercetask.presentation.sign_up

import com.google.firebase.auth.FirebaseUser

data class SignUpUIState (
    val isLoading:Boolean = false,
    val user: FirebaseUser? = null,
    val error:String =""
)