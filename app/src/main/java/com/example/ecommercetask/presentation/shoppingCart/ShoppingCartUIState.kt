package com.example.ecommercetask.presentation.shoppingCart

import com.example.ecommercetask.data.remote.firestore.Product

data class ShoppingCartUIState(
    val isLoading:Boolean = false,
    val product: List<Product?>? = null,
    val error:String ="",
)
