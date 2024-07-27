package com.example.ecommercetask.domain.use_cases

import com.example.ecommercetask.data.AuthOperations
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authOperations: AuthOperations
) {

    operator fun invoke(){
        authOperations.signOut()
    }
}