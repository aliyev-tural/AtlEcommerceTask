package com.example.ecommercetask.presentation.home

import com.example.ecommercetask.data.remote.firestore.Product

data class HomeUIState(
    val isLoading:Boolean = false,
    val products: MutableList<Product>? = null,
    val error:String ="",
)
