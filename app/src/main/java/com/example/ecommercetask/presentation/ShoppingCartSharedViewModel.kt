package com.example.ecommercetask.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercetask.data.Resource
import com.example.ecommercetask.data.remote.firestore.ProductRepository
import com.example.ecommercetask.presentation.product.ProductUIState
import com.example.ecommercetask.presentation.shoppingCart.ShoppingCartUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingCartSharedViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {


    private val _shoppingCartUiState = MutableStateFlow(ShoppingCartUIState())
    val shoppingCartUIState:StateFlow<ShoppingCartUIState> = _shoppingCartUiState.asStateFlow()

    private val _uiState = MutableStateFlow<ProductUIState>(ProductUIState())
    val uiState: StateFlow<ProductUIState> = _uiState.asStateFlow()




    fun getProduct(productId:String){
        viewModelScope.launch {
            _uiState.value = ProductUIState(isLoading = true )
            val result = productRepository.getProductByDocumentId(productId)
            Log.d("PRODUCT__FINISH","$productId")
            _uiState.value = ProductUIState(isLoading = false )

            when(result){
                is Resource.Success-> {
                    Log.d("PRODUCT__FINISH","${result.data}")
                    _uiState.value = ProductUIState(product = result.data)
                }
                is Resource.Error -> _uiState.value = ProductUIState(error = result.message)
            }
        }
    }

    fun addProductToShoppingCart(productId:String,userId:String){
        viewModelScope.launch {
            val result = productRepository.addProductToCart(productId,userId)
            when(result){
                is Resource.Success-> _uiState.value = _uiState.value.copy(isAddedToCart = true)
                is Resource.Error -> _uiState.value = _uiState.value.copy(error = result.message)
            }
        }
    }


}