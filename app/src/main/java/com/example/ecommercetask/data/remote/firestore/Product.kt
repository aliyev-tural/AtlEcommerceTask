package com.example.ecommercetask.data.remote.firestore

import java.util.UUID

data class Product(
    val id: String?,
    val title: String?,
    val price: Double?,
    val description: String?,
    val image: String?,



){
    constructor():this(id = null, title = null, price = null, description =null, image =null)

}
