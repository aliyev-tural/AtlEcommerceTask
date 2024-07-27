package com.example.ecommercetask.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercetask.data.Resource
import com.example.ecommercetask.data.remote.firestore.ProductRepository
import com.example.ecommercetask.presentation.sign_in.SignInUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
):ViewModel() {
    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState())
    val uiState: StateFlow<HomeUIState> = _uiState.asStateFlow()



    init {
        getProducts()
    }


    fun getProducts(){
        viewModelScope.launch {
            _uiState.value = HomeUIState(isLoading = true)
            val result = productRepository.getProducts()
            _uiState.value = HomeUIState(isLoading = false)
            when(result){
                is Resource.Success-> _uiState.value = HomeUIState(products = result.data.toMutableList())
                is Resource.Error -> _uiState.value = HomeUIState(error = result.message)
            }

        }

    }

}