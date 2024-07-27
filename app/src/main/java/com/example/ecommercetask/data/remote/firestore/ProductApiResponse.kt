package com.example.ecommercetask.data.remote.firestore

import android.media.Rating

data class ProductApiResponse(
    val category: String?,
    val description: String?,
    val id: Int?,
    val image: String?,
    val price: Double?,
    val title: String?
)