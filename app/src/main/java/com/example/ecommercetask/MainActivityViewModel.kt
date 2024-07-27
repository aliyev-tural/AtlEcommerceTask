package com.example.ecommercetask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class MainActivityViewModel(): ViewModel() {

    private val _currentUser: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val currentUser: LiveData<FirebaseUser?> = _currentUser

    private val _isPostLoginWorkDone = MutableLiveData<Boolean>(false)
    val isPostLoginWorkDone: LiveData<Boolean> get() = _isPostLoginWorkDone

    fun setPostLoginWorkDone(done: Boolean) {
        _isPostLoginWorkDone.value = done
    }

    fun setCurrentUserValue(user: FirebaseUser?){
        _currentUser.value = user
    }
}