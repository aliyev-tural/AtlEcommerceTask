package com.example.ecommercetask.presentation.product

import com.example.ecommercetask.data.remote.firestore.Product

data class ProductUIState(
    val isLoading:Boolean = false,
    val product: Product? = null,
    val error:String ="",
    var isAddedToCart: Boolean = false
)

